package com.chatrobot.opendoor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chatrobot.opendoor.databinding.ActivitySettingsBinding

// 【只改点击文案，不会触发开门】
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = UnlockPrefs(this)
        val labels = prefs.labels()
        binding.step1.setText(labels.getOrElse(0) { "我的" })
        binding.step2.setText(labels.getOrElse(1) { "租约" })
        binding.step3.setText(labels.getOrElse(2) { "智能门锁" })
        binding.step4.setText(labels.getOrElse(3) { "蓝牙开门" })
        binding.saveButton.setOnClickListener {
            prefs.save(
                binding.step1.text?.toString().orEmpty(),
                binding.step2.text?.toString().orEmpty(),
                binding.step3.text?.toString().orEmpty(),
                binding.step4.text?.toString().orEmpty()
            )
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
