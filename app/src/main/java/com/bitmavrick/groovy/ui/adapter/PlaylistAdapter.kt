package com.bitmavrick.groovy.ui.adapter

import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.MediaStoreUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.fragment.GeneralSubFragment

class PlaylistAdapter(
    private val mainActivity: MainActivity,
    playlistList: MutableLiveData<MutableList<MediaStoreUtils.Playlist>>,
) : BaseAdapter<MediaStoreUtils.Playlist>
    (
    mainActivity,
    liveData = playlistList,
    sortHelper = StoreItemHelper(),
    naturalOrderHelper = null,
    initialSortType = Sorter.Type.ByTitleAscending,
    pluralStr = R.plurals.items,
    ownsView = true,
    defaultLayoutType = LayoutType.LIST
) {

    override val defaultCover = R.drawable.ic_default_cover_playlist

    override fun virtualTitleOf(item: MediaStoreUtils.Playlist): String {
        return context.getString(
            if (item is MediaStoreUtils.RecentlyAdded)
                R.string.recently_added else R.string.unknown_playlist
        )
    }

    override fun onClick(item: MediaStoreUtils.Playlist) {
        mainActivity.startFragment(
            GeneralSubFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt("Position", toRawPos(item))
                        putInt("Item", R.id.playlist)
                    }
            },
        )
    }

    override fun onMenu(item: MediaStoreUtils.Playlist, popupMenu: PopupMenu) {
        popupMenu.inflate(R.menu.more_menu_less)

        popupMenu.setOnMenuItemClickListener { it1 ->
            when (it1.itemId) {
                R.id.play_next -> {
                    val mediaController = mainActivity.getPlayer()
                    mediaController.addMediaItems(
                        mediaController.currentMediaItemIndex + 1,
                        item.songList,
                    )
                    true
                }
                else -> false
            }
        }
    }
}
