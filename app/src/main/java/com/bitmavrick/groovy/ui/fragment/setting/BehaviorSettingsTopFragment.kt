package com.bitmavrick.groovy.ui.fragment.setting

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.preference.Preference
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.ui.fragment.BasePreferenceFragment

class BehaviorSettingsTopFragment : BasePreferenceFragment(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_behavior, rootKey)
    }

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == "blacklist") {
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(System.currentTimeMillis().toString())
                .hide(supportFragmentManager.fragments.let { it[it.size - 1] })
                .add(R.id.container, BlacklistSettingsFragment())
                .commit()
        }
        return super.onPreferenceTreeClick(preference)
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
