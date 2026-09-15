package com.chatrobot.opendoor

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【纯文案判断下一步；无 Android 依赖，便于单测】
object BeikePages {
    enum class NavStep { TAP_UNLOCK, CLICK_LOCK, BACK_TO_LEASE, CLICK_LEASE, CLICK_MINE }

    // 【「租约」图标在新我的页更靠下，但仍须低于底栏】
    const val LEASE_TEXT_MAX_Y = 0.86f
    // 【横向五个入口里「租约」是第二格】
    const val MINE_LEASE_TAP_X = 0.30f
    const val MINE_LEASE_TAP_Y = 0.60f

    fun isHome(screen: String): Boolean {
        if (isLeaseBottomBar(screen) || isLeaseHub(screen) || isLandlordPage(screen)) return false
        return screen.contains("二手房") && screen.contains("新房") && screen.contains("首页")
    }

    // 【租约/房东底栏都有「房东」，主站首页没有】
    fun isLeaseBottomBar(screen: String): Boolean {
        return screen.contains("房东") && screen.contains("租约")
    }

    fun isLeaseHub(screen: String): Boolean {
        return screen.contains("切换租约") ||
            screen.contains("租后服务") ||
            (screen.contains("在租中") && screen.contains("租约"))
    }

    fun isLandlordPage(screen: String): Boolean {
        if (screen.contains("智能门锁") || screen.contains("切换租约") || screen.contains("租后服务")) {
            return false
        }
        return screen.contains("一键出租") || screen.contains("我是房东")
    }

    fun isLockPage(screen: String): Boolean {
        return screen.contains("临时密码") ||
            screen.contains("开门中") ||
            screen.contains("蓝牙开门")
    }

    fun hasLeaseEntry(screen: String, leaseLabel: String): Boolean {
        return screen.contains(leaseLabel) &&
            !isHome(screen) &&
            !isLeaseHub(screen) &&
            !isLeaseBottomBar(screen) &&
            !isLandlordPage(screen)
    }

    fun hasLockMenu(screen: String, lockLabel: String): Boolean {
        return screen.contains(lockLabel) && !isLockPage(screen)
    }

    // 【2026-09 我的页：购房计划书 + 横向「专属服务 / 租约 / 我的委托」】
    fun isMineHub(screen: String): Boolean {
        if (isLockPage(screen) || isLeaseHub(screen) || isLeaseBottomBar(screen) || isLandlordPage(screen)) {
            return false
        }
        if (screen.contains("智能门锁")) return false
        val iconRow = screen.contains("专属服务") && screen.contains("我的委托")
        val planCard = screen.contains("购房计划书") && screen.contains("浏览记录")
        return iconRow || planCard
    }

    fun navStep(screen: String, leaseLabel: String, lockLabel: String): NavStep {
        if (isLockPage(screen)) return NavStep.TAP_UNLOCK
        if (hasLockMenu(screen, lockLabel)) return NavStep.CLICK_LOCK
        if (isLandlordPage(screen)) return NavStep.BACK_TO_LEASE
        if (isLeaseHub(screen) || isLeaseBottomBar(screen)) return NavStep.CLICK_LOCK
        if (hasLeaseEntry(screen, leaseLabel) || isMineHub(screen)) return NavStep.CLICK_LEASE
        return NavStep.CLICK_MINE
    }
}
