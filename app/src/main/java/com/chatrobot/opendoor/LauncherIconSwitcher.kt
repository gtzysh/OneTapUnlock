package com.chatrobot.opendoor

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【先启用新 alias，再关掉其余，避免桌面图标空一拍】
object LauncherIconSwitcher {
    fun apply(context: Context, icon: LauncherIcon) {
        val pm = context.packageManager
        val selected = ComponentName(context, icon.aliasClass)
        pm.setComponentEnabledSetting(
            selected,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        for (other in LauncherIcon.entries) {
            if (other == icon) continue
            pm.setComponentEnabledSetting(
                ComponentName(context, other.aliasClass),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        UnlockPrefs(context).saveIconId(icon.id)
    }
}
