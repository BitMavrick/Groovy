package com.bitmavrick.groovy.ui.fragment.setting

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.preference.Preference
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.fragment.BasePreferenceFragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AboutSettingsTopFragment : BasePreferenceFragment(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_about, rootKey)
        val versionPrefs = findPreference<Preference>("app_version")
        val releaseType = findPreference<Preference>("package_type")
        versionPrefs!!.summary = "1.0.2"
        releaseType!!.summary = "release"
    }

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == "app_name") {
            val processColor = ColorUtils.getColor(
                MaterialColors.getColor(
                    requireView(),
                    android.R.attr.colorBackground
                ),
                ColorUtils.ColorType.COLOR_BACKGROUND,
                requireContext(),
                true
            )
            val drawable = GradientDrawable()
            drawable.color =
                ColorStateList.valueOf(processColor)
            drawable.cornerRadius = 64f
            val rootView = MaterialAlertDialogBuilder(requireContext())
                .setBackground(drawable)
                .setView(R.layout.dialog_about)
                .show()
            val versionTextView = rootView.findViewById<TextView>(R.id.version)!!
            versionTextView.text = "1.0.2"
        }
        return super.onPreferenceTreeClick(preference)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
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
