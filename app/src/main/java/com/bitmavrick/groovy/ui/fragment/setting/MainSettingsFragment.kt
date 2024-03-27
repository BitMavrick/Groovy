package com.bitmavrick.groovy.ui.fragment.setting

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.px
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.fragment.BaseFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.slider.Slider

class MainSettingsFragment : BaseFragment(false) {

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

        topAppBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val isLegacyProgressEnabled = prefs.getBoolean("default_progress_bar", false)
        val isContentBasedColorEnabled = prefs.getBoolean("content_based_color", true)
        val isTitleCentered = prefs.getBoolean("centered_title", true)
        val isTitleBold = prefs.getBoolean("bold_title", true)
        val activity = requireActivity()
        val bottomSheetFullSlider = activity.findViewById<Slider>(R.id.slider_vert)
        val bottomSheetFullSeekBar = activity.findViewById<SeekBar>(R.id.slider_squiggly)
        val bottomSheetFullTitle = activity.findViewById<TextView>(R.id.full_song_name)
        val bottomSheetFullSubtitle = activity.findViewById<TextView>(R.id.full_song_artist)
        val bottomSheetFullCoverFrame =
            activity.findViewById<MaterialCardView>(R.id.album_cover_frame)
        if (isLegacyProgressEnabled) {
            bottomSheetFullSlider.visibility = View.VISIBLE
            bottomSheetFullSeekBar.visibility = View.GONE
        } else {
            bottomSheetFullSlider.visibility = View.GONE
            bottomSheetFullSeekBar.visibility = View.VISIBLE
        }
        if (!isContentBasedColorEnabled) {
            (activity as MainActivity).getPlayerSheet().removeColorScheme()
        } else {
            (activity as MainActivity).getPlayerSheet().addColorScheme()
        }
        if (isTitleCentered) {
            bottomSheetFullTitle.gravity = Gravity.CENTER
            bottomSheetFullSubtitle.gravity = Gravity.CENTER
        } else {
            bottomSheetFullTitle.gravity = Gravity.CENTER_HORIZONTAL or Gravity.START
            bottomSheetFullSubtitle.gravity = Gravity.CENTER_HORIZONTAL or Gravity.START
        }
        if (isTitleBold && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            bottomSheetFullTitle.typeface = Typeface.create(null, 700, false)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            bottomSheetFullTitle.typeface = Typeface.create(null, 500, false)
        }
        bottomSheetFullCoverFrame.radius = prefs.getInt(
            "album_round_corner",
            requireContext().resources.getInteger(R.integer.round_corner_radius)
        ).px.toFloat()
    }
}