package com.bitmavrick.groovy.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.ui.fragment.AdapterFragment

class ViewPager2Adapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        val tabs: ArrayList<Int> = arrayListOf(
            R.id.songs,
            R.id.albums,
            R.id.artists,
            R.id.genres,
            R.id.dates,
            // R.id.folders,
            // R.id.detailed_folders,
            // R.id.playlists,
        )
    }

    fun getLabelResId(position: Int): Int =
        when (tabs[position]) {
            R.id.songs -> R.string.category_songs
            R.id.albums -> R.string.category_albums
            R.id.artists -> R.string.category_artists
            R.id.genres -> R.string.category_genres
            R.id.dates -> R.string.category_dates
            // R.id.folders -> R.string.filesystem
            // R.id.detailed_folders -> R.string.folders
            // R.id.playlists -> R.string.category_playlists
            else -> throw IllegalArgumentException("Invalid position: $position")
        }

    override fun getItemCount(): Int = tabs.count()

    override fun createFragment(position: Int): Fragment =
        AdapterFragment().apply {
            arguments = Bundle().apply {
                putInt("ID", tabs[position])
            }
        }
}
