package com.bitmavrick.groovy.ui.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.fragment.BaseFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.color.MaterialColors

class LyricSettingsFragment : BaseFragment(false) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_settings, container, false)
        val topAppBar = rootView.findViewById<MaterialToolbar>(R.id.topAppBar)

        val collapsingToolbar =
            rootView.findViewById<CollapsingToolbarLayout>(R.id.collapsingtoolbar)
        val processColor = ColorUtils.getColor(
            MaterialColors.getColor(
                topAppBar,
                android.R.attr.colorBackground
            ),
            ColorUtils.ColorType.COLOR_BACKGROUND,
            requireContext(),
            true
        )
        val processColorElevated = ColorUtils.getColor(
            MaterialColors.getColor(
                topAppBar,
                android.R.attr.colorBackground
            ),
            ColorUtils.ColorType.TOOLBAR_ELEVATED,
            requireContext(),
            true
        )

        collapsingToolbar.setBackgroundColor(processColor)
        collapsingToolbar.setContentScrimColor(processColorElevated)
        collapsingToolbar.title = getString(R.string.settings_lyric)

        topAppBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        childFragmentManager
            .beginTransaction()
            .addToBackStack(System.currentTimeMillis().toString())
            .add(R.id.settings, LyricSettingsTopFragment())
            .commit()

        return rootView
    }

}