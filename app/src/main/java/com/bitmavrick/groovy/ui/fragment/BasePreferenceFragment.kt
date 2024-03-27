package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.bitmavrick.groovy.core.util.ColorUtils
import com.google.android.material.color.MaterialColors

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorBackground = ColorUtils.getColor(
            MaterialColors.getColor(view, android.R.attr.colorBackground),
            ColorUtils.ColorType.COLOR_BACKGROUND,
            requireContext(),
            true
        )
        view.setBackgroundColor(colorBackground)
    }
}