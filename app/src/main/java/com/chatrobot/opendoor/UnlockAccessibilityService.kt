package com.chatrobot.opendoor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【只处理贝壳找房；点中开门后回桌面并提示开门成功】
class UnlockAccessibilityService : AccessibilityService() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        instance = this
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (instance === this) instance = null
        coordinator?.cancel()
        coordinator = null
        return super.onUnbind(intent)
    }

    override fun onInterrupt() {
        coordinator?.cancel()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val session = coordinator ?: return
        if (!session.running) return
        // 【门锁页是 H5 时，事件包名可能是 WebView，不能只认贝壳包名】
        val root = rootInActiveWindow ?: return
        session.onBeikeWindow(root)
    }

    fun beginUnlock() {
        coordinator?.cancel()
        coordinator = UnlockCoordinator(
            service = this,
            prefs = UnlockPrefs(this),
            onFinished = { outcome, extra ->
                coordinator = null
                if (outcome == UnlockOutcome.FAILED) {
                    Toast.makeText(applicationContext, failMessage(extra), Toast.LENGTH_LONG).show()
                    vibrateFor(UnlockOutcome.FAILED)
                } else {
                    goHomeAndToastSuccess()
                }
            }
        )
        coordinator?.start()
    }

    fun stopUnlock() {
        coordinator?.cancel()
        coordinator = null
    }

    private fun goHomeAndToastSuccess() {
        vibrateFor(UnlockOutcome.SUCCESS)
        // 【先回桌面，再弹出和贝壳「开门中」同款的开门成功条】
        performGlobalAction(GLOBAL_ACTION_HOME)
        handler.postDelayed({
            val hud = Intent(this, SuccessHudActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            startActivity(hud)
        }, 480)
    }

    private fun failMessage(extra: String?): String {
        return extra?.let { reason ->
            if (reason == "超时") getString(R.string.err_timeout) else "开门失败：$reason"
        } ?: getString(R.string.fail_title)
    }

    private fun vibrateFor(outcome: UnlockOutcome) {
        val vibrator = if (Build.VERSION.SDK_INT >= 31) {
            getSystemService(VibratorManager::class.java)?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        } ?: return
        if (!vibrator.hasVibrator()) return
        val ms = if (outcome == UnlockOutcome.FAILED) 40L else 70L
        vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    companion object {
        @Volatile
        var instance: UnlockAccessibilityService? = null
            private set

        @Volatile
        private var coordinator: UnlockCoordinator? = null
    }
}
