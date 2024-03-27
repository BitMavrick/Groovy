package com.bitmavrick.groovy.ui.adapter

import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.MediaStoreUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.fragment.GeneralSubFragment

class GenreAdapter(
    private val mainActivity: MainActivity,
    genreList: MutableLiveData<MutableList<MediaStoreUtils.Genre>>,
) : BaseAdapter<MediaStoreUtils.Genre>
    (
    mainActivity,
    liveData = genreList,
    sortHelper = StoreItemHelper(),
    naturalOrderHelper = null,
    initialSortType = Sorter.Type.ByTitleAscending,
    pluralStr = R.plurals.items,
    ownsView = true,
    defaultLayoutType = LayoutType.LIST
) {

    override val defaultCover = R.drawable.ic_default_cover_genre

    override fun virtualTitleOf(item: MediaStoreUtils.Genre): String {
        return context.getString(R.string.unknown_genre)
    }

    override fun onClick(item: MediaStoreUtils.Genre) {
        mainActivity.startFragment(
            GeneralSubFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt("Position", toRawPos(item))
                        putInt("Item", R.id.genre)
                    }
            },
        )
    }

    override fun onMenu(item: MediaStoreUtils.Genre, popupMenu: PopupMenu) {
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
