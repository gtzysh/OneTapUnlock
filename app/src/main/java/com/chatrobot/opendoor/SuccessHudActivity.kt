package com.chatrobot.opendoor

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

// 【桌面正中弹出和贝壳「开门中」同款的黑底条，不挡住桌面点击】
class SuccessHudActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val dismiss = Runnable { finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        setContentView(R.layout.activity_success_hud)
        handler.postDelayed(dismiss, SHOW_MS)
    }

    override fun onDestroy() {
        handler.removeCallbacks(dismiss)
        super.onDestroy()
    }

    companion object {
        private const val SHOW_MS = 1800L
    }
}
