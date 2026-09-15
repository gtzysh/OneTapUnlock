package com.chatrobot.opendoor

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityNodeInfo

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【按「当前页有没有下一入口」推进：首页→我的→租约→智能门锁→圆钮】
class UnlockCoordinator(
    private val service: AccessibilityService,
    private val prefs: UnlockPrefs,
    private val onFinished: (UnlockOutcome, String?) -> Unit
) {
    @Volatile
    var running: Boolean = false
        private set

    private val handler = Handler(Looper.getMainLooper())
    private var lastClickAt = 0L
    private var phase = Phase.GO_MINE
    private var tapIndex = 0
    private var lockCardTries = 0
    private var leftHome = false
    private var openingSince = 0L
    private val timeout = Runnable {
        if (running) finish(UnlockOutcome.FAILED, "超时")
    }
    // 【首页广告停住后可能不再发无障碍事件，定时再扫一次当前窗口】
    private val tick = object : Runnable {
        override fun run() {
            if (!running) return
            onBeikeWindow(service.rootInActiveWindow)
            handler.postDelayed(this, TICK_MS)
        }
    }

    fun start() {
        cancel()
        running = true
        lastClickAt = 0L
        phase = Phase.GO_MINE
        tapIndex = 0
        lockCardTries = 0
        leftHome = false
        openingSince = 0L
        handler.postDelayed(timeout, TOTAL_TIMEOUT_MS)
        handler.postDelayed(tick, 400)
    }

    fun cancel() {
        running = false
        handler.removeCallbacks(timeout)
        handler.removeCallbacks(tick)
    }

    fun onBeikeWindow(root: AccessibilityNodeInfo?) {
        if (!running || root == null) return
        val now = System.currentTimeMillis()
        val screen = NodeClicker.normalizeScreen(NodeClicker.collectVisibleText(root))
        if (phase == Phase.WAIT_OPENING) {
            waitOpening(screen, now)
            return
        }
        if (BeikePages.isLockPage(screen)) {
            phase = Phase.TAP
            tapUnlock(screen, now)
            return
        }
        // 【导航只点贝壳自己，避免手势打到桌面或系统栏】
        val pkg = root.packageName?.toString().orEmpty()
        if (pkg.isNotEmpty() && pkg != BeikePackages.BEIKE) return
        if (now - lastClickAt < NAV_COOLDOWN_MS) return
        val lease = labels().getOrElse(1) { "租约" }
        val lock = labels().getOrElse(2) { "智能门锁" }
        if (BeikePages.isLeaseBottomBar(screen) || BeikePages.isLeaseHub(screen) || BeikePages.hasLockMenu(screen, lock)) {
            leftHome = true
        }
        when (BeikePages.navStep(screen, lease, lock)) {
            BeikePages.NavStep.TAP_UNLOCK -> {
                phase = Phase.TAP
                tapUnlock(screen, now)
            }
            BeikePages.NavStep.CLICK_LOCK -> clickLockMenu(root, now)
            BeikePages.NavStep.BACK_TO_LEASE -> clickBackToLease(root, now)
            BeikePages.NavStep.CLICK_LEASE -> clickLease(root, screen, now)
            BeikePages.NavStep.CLICK_MINE -> clickMine(root, screen, now)
        }
    }

    private fun labels(): List<String> = prefs.labels()

    private fun clickMine(root: AccessibilityNodeInfo, screen: String, now: Long) {
        // 【「我的」和「房东」都在右下角，见到房东后绝不能再点我的】
        if (leftHome ||
            BeikePages.isLeaseBottomBar(screen) ||
            BeikePages.isLeaseHub(screen) ||
            BeikePages.isLandlordPage(screen) ||
            BeikePages.hasLockMenu(screen, labels().getOrElse(2) { "智能门锁" }) ||
            BeikePages.isMineHub(screen)
        ) {
            return
        }
        lastClickAt = now
        val mineLabel = labels().getOrElse(0) { "我的" }
        if (NodeClicker.clickBottomTab(service, root, mineLabel)) {
            leftHome = true
        }
    }

    private fun clickLease(root: AccessibilityNodeInfo, screen: String, now: Long) {
        lastClickAt = now
        leftHome = true
        val lease = labels().getOrElse(1) { "租约" }
        val maxY = BeikePages.LEASE_TEXT_MAX_Y
        if (NodeClicker.clickTextAbove(service, root, lease, maxYRatio = maxY, preferLargest = false)) return
        if (NodeClicker.clickTextAbove(service, root, "我的租约", maxYRatio = maxY, preferLargest = false)) return
        // 【无障碍读不到「租约」时，只在已认出的我的页补点第二格】
        if (BeikePages.isMineHub(screen)) {
            NodeClicker.tapPercent(service, BeikePages.MINE_LEASE_TAP_X, BeikePages.MINE_LEASE_TAP_Y)
        }
    }

    private fun clickLockMenu(root: AccessibilityNodeInfo, now: Long) {
        lastClickAt = now
        leftHome = true
        val lock = labels().getOrElse(2) { "智能门锁" }
        if (NodeClicker.clickTextAbove(service, root, lock, maxYRatio = 0.62f, minXRatio = 0.45f, preferLargest = false)) {
            return
        }
        lockCardTries++
        if (lockCardTries <= LOCK_CARD_MAX) {
            // 【租约页右侧门锁卡片，不要点底栏】
            NodeClicker.tapPercent(service, 0.75f, 0.28f)
        }
    }

    private fun clickBackToLease(root: AccessibilityNodeInfo, now: Long) {
        lastClickAt = now
        leftHome = true
        val lease = labels().getOrElse(1) { "租约" }
        NodeClicker.clickBottomTab(service, root, lease)
    }

    private fun tapUnlock(screen: String, now: Long) {
        if (screen.contains("开门中")) {
            phase = Phase.WAIT_OPENING
            openingSince = now
            return
        }
        val failHit = FAIL_KEYS.firstOrNull { screen.contains(it) }
        if (failHit != null) {
            finish(UnlockOutcome.FAILED, failHit)
            return
        }
        if (now - lastClickAt < TAP_COOLDOWN_MS) return
        if (tapIndex >= TAP_Y.size) {
            finish(UnlockOutcome.FAILED, "没点到蓝牙开门圆钮")
            return
        }
        val y = TAP_Y[tapIndex]
        tapIndex++
        lastClickAt = now
        NodeClicker.tapPercent(service, 0.50f, y)
    }

    private fun waitOpening(screen: String, now: Long) {
        val failHit = FAIL_KEYS.firstOrNull { screen.contains(it) }
        if (failHit != null) {
            finish(UnlockOutcome.FAILED, failHit)
            return
        }
        val okHit = SUCCESS_KEYS.firstOrNull { screen.contains(it) }
        if (okHit != null) {
            finish(UnlockOutcome.SUCCESS, okHit)
            return
        }
        if (screen.contains("开门中")) {
            if (now - openingSince > OPENING_MAX_MS) {
                finish(UnlockOutcome.FAILED, "开门中超时")
            }
            return
        }
        if (now - openingSince > 800) {
            finish(UnlockOutcome.SUCCESS, "开门中")
        }
    }

    private fun finish(outcome: UnlockOutcome, extra: String?) {
        if (!running) return
        running = false
        handler.removeCallbacks(timeout)
        handler.removeCallbacks(tick)
        onFinished(outcome, extra)
    }

    private enum class Phase { GO_MINE, TAP, WAIT_OPENING }

    companion object {
        private const val TOTAL_TIMEOUT_MS = 55_000L
        private const val TICK_MS = 1200L
        private const val NAV_COOLDOWN_MS = 1600L
        private const val LOCK_CARD_MAX = 4
        private const val TAP_COOLDOWN_MS = 900L
        private const val OPENING_MAX_MS = 20_000L
        private val TAP_Y = floatArrayOf(0.33f, 0.36f, 0.39f, 0.42f, 0.35f, 0.38f, 0.31f, 0.44f)
        private val SUCCESS_KEYS = listOf("开锁成功", "开门成功", "解锁成功", "已开锁", "开锁完成")
        private val FAIL_KEYS = listOf("开锁失败", "开门失败", "解锁失败", "连接失败", "蓝牙未开启", "操作失败")
    }
}
