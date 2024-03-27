package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.util.UnstableApi
import androidx.viewpager2.widget.ViewPager2
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.adapter.ViewPager2Adapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.color.MaterialColors
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@androidx.annotation.OptIn(UnstableApi::class)
class ViewPagerFragment : BaseFragment(true) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_viewpager, container, false)
        val tabLayout = rootView.findViewById<TabLayout>(R.id.tab_layout)
        val topAppBar = rootView.findViewById<MaterialToolbar>(R.id.topAppBar)
        val appBarLayout = rootView.findViewById<AppBarLayout>(R.id.appbarlayout)
        val viewPager2 = rootView.findViewById<ViewPager2>(R.id.fragment_viewpager)

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    (requireActivity() as MainActivity).startFragment(SearchFragment())
                }

                else -> throw IllegalStateException()
            }
            true
        }

        topAppBar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).navigateDrawer(viewPager2.currentItem)
        }

        val processColor = ColorUtils.getColor(
            MaterialColors.getColor(
                topAppBar,
                com.google.android.material.R.attr.colorSurfaceContainer
            ),
            ColorUtils.ColorType.COLOR_BACKGROUND_TINTED,
            requireContext(),
            true
        )

        topAppBar.setBackgroundColor(processColor)
        appBarLayout.setBackgroundColor(processColor)
        tabLayout.setBackgroundColor(processColor)

        viewPager2.offscreenPageLimit = 9999
        val adapter = ViewPager2Adapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(adapter.getLabelResId(position))
        }.attach()


        val lastTab = tabLayout.getTabAt(tabLayout.tabCount - 1)!!.view
        val firstTab = tabLayout.getTabAt(0)!!.view
        val lastParam = lastTab.layoutParams as ViewGroup.MarginLayoutParams
        val firstParam = firstTab.layoutParams as ViewGroup.MarginLayoutParams
        lastParam.marginEnd = resources.getDimension(R.dimen.tab_layout_content_padding).toInt()
        firstParam.marginStart = resources.getDimension(R.dimen.tab_layout_content_padding).toInt()
        lastTab.layoutParams = lastParam
        firstTab.layoutParams = firstParam

        return rootView
    }
}