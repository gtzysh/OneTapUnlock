package com.chatrobot.opendoor

import android.content.Context

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【保存贝壳页面上的按钮文案，改版后可在设置里改，不必重装】
class UnlockPrefs(context: Context) {
    private val sp = context.getSharedPreferences("unlock", Context.MODE_PRIVATE)

    fun labels(): List<String> = listOf(
        sp.getString(KEY1, "我的") ?: "我的",
        sp.getString(KEY2, "租约") ?: "租约",
        sp.getString(KEY3, "智能门锁") ?: "智能门锁",
        sp.getString(KEY4, "蓝牙开门") ?: "蓝牙开门"
    ).map { it.trim() }.filter { it.isNotEmpty() }

    fun save(step1: String, step2: String, step3: String, step4: String) {
        sp.edit()
            .putString(KEY1, step1.trim().ifEmpty { "我的" })
            .putString(KEY2, step2.trim().ifEmpty { "租约" })
            .putString(KEY3, step3.trim().ifEmpty { "智能门锁" })
            .putString(KEY4, step4.trim().ifEmpty { "蓝牙开门" })
            .apply()
    }

    companion object {
        private const val KEY1 = "step1"
        private const val KEY2 = "step2"
        private const val KEY3 = "step3"
        private const val KEY4 = "step4"
    }
}
