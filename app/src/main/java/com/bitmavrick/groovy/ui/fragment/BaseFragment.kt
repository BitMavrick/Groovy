package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialSharedAxis

abstract class BaseFragment(val wantsPlayer: Boolean? = null) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

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

    final override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) return
        if (wantsPlayer != null) {
            (requireActivity() as MainActivity).getPlayerSheet().visible = wantsPlayer
        }
    }
}