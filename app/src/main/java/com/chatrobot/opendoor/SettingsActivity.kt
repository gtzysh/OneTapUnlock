package com.chatrobot.opendoor

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chatrobot.opendoor.databinding.ActivitySettingsBinding

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【改点击文案或桌面图标，都不会触发开门】
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val iconButtons = mutableMapOf<LauncherIcon, LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = UnlockPrefs(this)
        val labels = prefs.labels()
        binding.step1.setText(labels.getOrElse(0) { "我的" })
        binding.step2.setText(labels.getOrElse(1) { "租约" })
        binding.step3.setText(labels.getOrElse(2) { "智能门锁" })
        binding.step4.setText(labels.getOrElse(3) { "蓝牙开门" })
        bindIconPicker(LauncherIcon.fromId(prefs.iconId()))
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

    private fun bindIconPicker(selected: LauncherIcon) {
        binding.iconPicker.removeAllViews()
        iconButtons.clear()
        val pad = dp(8)
        for (icon in LauncherIcon.entries) {
            val item = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                background = ContextCompat.getDrawable(this@SettingsActivity, R.drawable.bg_icon_choice)
                setPadding(pad, pad, pad, pad)
                val params = LinearLayout.LayoutParams(dp(76), LinearLayout.LayoutParams.WRAP_CONTENT)
                params.marginEnd = dp(8)
                layoutParams = params
                isSelected = icon == selected
            }
            val image = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(dp(52), dp(52))
                setImageResource(icon.previewRes)
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            val caption = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = dp(6) }
                setText(icon.titleRes)
                setTextColor(ContextCompat.getColor(this@SettingsActivity, R.color.muted))
                textSize = 11f
                gravity = Gravity.CENTER
            }
            item.addView(image)
            item.addView(caption)
            item.setOnClickListener { onIconPicked(icon) }
            binding.iconPicker.addView(item)
            iconButtons[icon] = item
        }
    }

    private fun onIconPicked(icon: LauncherIcon) {
        LauncherIconSwitcher.apply(this, icon)
        for ((key, view) in iconButtons) {
            view.isSelected = key == icon
        }
        Toast.makeText(this, R.string.icon_changed, Toast.LENGTH_LONG).show()
    }

    private fun dp(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
