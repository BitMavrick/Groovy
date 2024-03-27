package com.bitmavrick.groovy.core.util

import com.bitmavrick.groovy.ui.adapter.AlbumAdapter
import com.bitmavrick.groovy.ui.adapter.ArtistAdapter
import com.bitmavrick.groovy.ui.adapter.BaseAdapter
import com.bitmavrick.groovy.ui.adapter.DateAdapter
import com.bitmavrick.groovy.ui.adapter.GenreAdapter
import com.bitmavrick.groovy.ui.adapter.PlaylistAdapter
import com.bitmavrick.groovy.ui.adapter.SongAdapter

object FileOpUtils {
    fun getAdapterType(adapter: BaseAdapter<*>) =
        when (adapter) {
            is AlbumAdapter -> {
                0
            }

            is ArtistAdapter -> {
                1
            }

            is DateAdapter -> {
                2
            }

            is GenreAdapter -> {
                3
            }

            is PlaylistAdapter -> {
                4
            }

            is SongAdapter -> {
                5
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
}