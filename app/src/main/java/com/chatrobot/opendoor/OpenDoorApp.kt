package com.chatrobot.opendoor

import android.app.Application

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【应用入口，便于无障碍服务取 Context】
class OpenDoorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: OpenDoorApp
            private set
    }
}
