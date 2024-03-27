package com.bitmavrick.groovy.ui.fragment.setting

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.fragment.BasePreferenceFragment

class AppearanceSettingsTopFragment : BasePreferenceFragment(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_appearance, rootKey)
    }

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "theme_mode" -> {
                when (sharedPreferences?.getString("theme_mode", "0")) {
                    "0" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }

                    "1" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                    "2" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }

            "oled" -> {
                if (sharedPreferences?.getBoolean("oled", false) == true) {
                    ColorUtils.overrideGlobalAmoledColor = true
                    requireActivity().recreate()
                } else {
                    ColorUtils.overrideGlobalAmoledColor = false
                    requireActivity().recreate()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

}
