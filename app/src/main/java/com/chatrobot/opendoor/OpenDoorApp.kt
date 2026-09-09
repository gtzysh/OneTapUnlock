package com.chatrobot.opendoor

import android.app.Application

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
