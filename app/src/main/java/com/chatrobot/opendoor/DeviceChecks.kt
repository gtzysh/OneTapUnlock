package com.chatrobot.opendoor

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager

object BeikePackages {
    const val BEIKE = "com.lianjia.beike"
}

// 【检查贝壳是否安装、无障碍是否真正连上】
object DeviceChecks {
    fun isBeikeInstalled(context: Context): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= 33) {
                context.packageManager.getPackageInfo(
                    BeikePackages.BEIKE,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(BeikePackages.BEIKE, 0)
            }
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun isUnlockServiceEnabled(context: Context): Boolean {
        val expected = ComponentName(context, UnlockAccessibilityService::class.java)
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledList = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        if (enabledList.any {
                val info = it.resolveInfo.serviceInfo
                info.packageName == expected.packageName && info.name == expected.className
            }
        ) {
            return true
        }
        val enabled = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false
        val flattened = expected.flattenToString()
        val shortName = expected.flattenToShortString()
        val splitter = TextUtils.SimpleStringSplitter(':')
        splitter.setString(enabled)
        while (splitter.hasNext()) {
            val item = splitter.next()
            if (item.equals(flattened, ignoreCase = true) || item.equals(shortName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
