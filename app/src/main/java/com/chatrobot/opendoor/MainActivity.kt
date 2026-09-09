package com.chatrobot.opendoor

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chatrobot.opendoor.databinding.ActivityGuideBinding
import com.chatrobot.opendoor.databinding.ActivityMainBinding

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【桌面点图标 = 立刻开门；结果 Intent 只展示回传，不再连开一次】
class MainActivity : AppCompatActivity() {
    private var bindRetries = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatch(intent, fromCreate = true)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        dispatch(intent, fromCreate = false)
    }

    private fun dispatch(intent: Intent, fromCreate: Boolean) {
        if (showResultIfPresent(intent)) return
        if (isFromRecents(intent)) {
            showIdle()
            return
        }
        if (!fromCreate && intent.action == Intent.ACTION_MAIN) {
            bindRetries = 0
        }
        handleLaunch()
    }

    private fun showResultIfPresent(intent: Intent): Boolean {
        if (!intent.getBooleanExtra(EXTRA_SHOW_RESULT, false)) return false
        val outcome = runCatching {
            UnlockOutcome.valueOf(intent.getStringExtra(EXTRA_OUTCOME) ?: UnlockOutcome.CLICKED.name)
        }.getOrDefault(UnlockOutcome.CLICKED)
        showStatus(
            kicker = getString(R.string.kicker_result),
            title = intent.getStringExtra(EXTRA_TITLE) ?: getString(R.string.ok_clicked),
            detail = intent.getStringExtra(EXTRA_DETAIL).orEmpty(),
            meta = intent.getStringExtra(EXTRA_TIME),
            icon = when (outcome) {
                UnlockOutcome.SUCCESS -> R.drawable.ic_hero_success
                UnlockOutcome.CLICKED -> R.drawable.ic_hero_warn
                UnlockOutcome.FAILED -> R.drawable.ic_hero_fail
            },
            primary = getString(R.string.retry),
            secondary = getString(R.string.settings_title),
            onPrimary = { startUnlockNow() },
            onSecondary = { startActivity(Intent(this, SettingsActivity::class.java)) }
        )
        return true
    }

    private fun handleLaunch() {
        if (!DeviceChecks.isBeikeInstalled(this)) {
            showGuide(getString(R.string.err_no_beike))
            return
        }
        if (!DeviceChecks.isUnlockServiceEnabled(this)) {
            showGuide(null)
            return
        }
        if (UnlockAccessibilityService.instance == null && bindRetries < 4) {
            bindRetries++
            showConnecting()
            window.decorView.postDelayed({ handleLaunch() }, 400)
            return
        }
        if (UnlockAccessibilityService.instance == null) {
            showGuide("无障碍已开，但服务还没连上。请到系统设置里关掉再打开「一键开门辅助」，然后回到桌面再点图标。")
            return
        }
        bindRetries = 0
        startUnlockNow()
    }

    private fun startUnlockNow() {
        showStatus(
            kicker = getString(R.string.kicker_ready),
            title = getString(R.string.status_running),
            detail = getString(R.string.status_running_detail),
            meta = null,
            icon = R.drawable.ic_hero_lock,
            primary = getString(R.string.cancel),
            secondary = getString(R.string.settings_title),
            onPrimary = {
                UnlockAccessibilityService.instance?.stopUnlock()
                finish()
            },
            onSecondary = { startActivity(Intent(this, SettingsActivity::class.java)) }
        )
        UnlockAccessibilityService.instance?.beginUnlock()
        val launch = packageManager.getLaunchIntentForPackage(BeikePackages.BEIKE)
        if (launch == null) {
            showGuide(getString(R.string.err_no_beike))
            return
        }
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(launch)
    }

    private fun showConnecting() {
        showStatus(
            kicker = getString(R.string.kicker_ready),
            title = getString(R.string.status_connecting),
            detail = getString(R.string.status_connecting_detail),
            meta = null,
            icon = R.drawable.ic_hero_lock,
            primary = getString(R.string.cancel),
            secondary = getString(R.string.settings_title),
            onPrimary = { finish() },
            onSecondary = { startActivity(Intent(this, SettingsActivity::class.java)) }
        )
    }

    private fun showIdle() {
        showStatus(
            kicker = getString(R.string.kicker_ready),
            title = getString(R.string.status_idle),
            detail = getString(R.string.status_idle_detail),
            meta = null,
            icon = R.drawable.ic_hero_lock,
            primary = getString(R.string.retry),
            secondary = getString(R.string.settings_title),
            onPrimary = { startUnlockNow() },
            onSecondary = { startActivity(Intent(this, SettingsActivity::class.java)) }
        )
    }

    private fun showStatus(
        kicker: String,
        title: String,
        detail: String,
        meta: String?,
        icon: Int,
        primary: String,
        secondary: String,
        onPrimary: () -> Unit,
        onSecondary: () -> Unit
    ) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.heroIcon.setImageResource(icon)
        binding.kickerText.text = kicker
        binding.statusText.text = title
        binding.detailText.text = detail
        if (meta.isNullOrBlank()) {
            binding.metaText.visibility = View.GONE
        } else {
            binding.metaText.visibility = View.VISIBLE
            binding.metaText.text = meta
            val color = when {
                title == getString(R.string.fail_title) -> R.color.danger
                title == getString(R.string.ok_clicked) -> R.color.warn
                else -> R.color.accent
            }
            binding.metaText.setTextColor(ContextCompat.getColor(this, color))
        }
        binding.primaryButton.text = primary
        binding.secondaryButton.text = secondary
        binding.primaryButton.setOnClickListener { onPrimary() }
        binding.secondaryButton.setOnClickListener { onSecondary() }
    }

    private fun showGuide(error: String?) {
        val binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!error.isNullOrBlank()) {
            binding.guideBody.text = error
        }
        binding.openA11yButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        binding.openSettingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        if (error == getString(R.string.err_no_beike)) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun isFromRecents(intent: Intent?): Boolean {
        if (intent == null) return false
        return intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0
    }

    companion object {
        const val EXTRA_SHOW_RESULT = "show_result"
        const val EXTRA_OUTCOME = "outcome"
        const val EXTRA_TITLE = "title"
        const val EXTRA_DETAIL = "detail"
        const val EXTRA_TIME = "time"
    }
}
