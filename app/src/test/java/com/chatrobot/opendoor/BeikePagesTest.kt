package com.chatrobot.opendoor

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【用 2026-09-15「我的」页截图文案锁定下一步必须是点租约】
class BeikePagesTest {

    // 【截图可见文字串在一起，模拟无障碍 normalizeScreen】
    private val minePageSep2026 = (
        "我的购房计划书填需求获取相似人群真实交易案例" +
            "找房专家轻松帮你全城找房帮我找房" +
            "诚邀您对贝壳的服务进行评价去评价" +
            "我的关注浏览记录看房管理交易订单钱包" +
            "关注作者专属服务租约我的委托优惠券卖房" +
            "全程守护你的隐私安全免打扰隐私设置专属推荐" +
            "首页我家AI消息我的"
        )

    @Test
    fun minePage2026GoesToLease() {
        assertEquals(
            BeikePages.NavStep.CLICK_LEASE,
            BeikePages.navStep(minePageSep2026, "租约", "智能门锁")
        )
    }

    @Test
    fun mineHubWithoutLeaseTextStillGoesToLease() {
        val screen = "购房计划书浏览记录看房管理专属服务我的委托优惠券首页我家AI消息我的"
        assertTrue(BeikePages.isMineHub(screen))
        assertEquals(
            BeikePages.NavStep.CLICK_LEASE,
            BeikePages.navStep(screen, "租约", "智能门锁")
        )
    }

    @Test
    fun fiveTabHomeStillGoesToMine() {
        val screen = "二手房新房首页我家AI消息我的"
        assertFalse(BeikePages.isMineHub(screen))
        assertEquals(
            BeikePages.NavStep.CLICK_MINE,
            BeikePages.navStep(screen, "租约", "智能门锁")
        )
    }

    @Test
    fun leaseBottomBarGoesToLockNotLease() {
        val screen = "智能门锁切换租约租房租约房东"
        assertFalse(BeikePages.isMineHub(screen))
        assertEquals(
            BeikePages.NavStep.CLICK_LOCK,
            BeikePages.navStep(screen, "租约", "智能门锁")
        )
    }

    @Test
    fun lockPageGoesToUnlock() {
        assertEquals(
            BeikePages.NavStep.TAP_UNLOCK,
            BeikePages.navStep("临时密码蓝牙开门", "租约", "智能门锁")
        )
    }
}
