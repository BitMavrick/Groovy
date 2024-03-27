package com.bitmavrick.groovy.ui.adapter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.MediaStoreUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.fragment.GeneralSubFragment
import com.bitmavrick.groovy.ui.viewmodel.LibraryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumAdapter(
    private val mainActivity: MainActivity,
    albumList: MutableLiveData<MutableList<MediaStoreUtils.Album>>?,
    ownsView: Boolean = true,
    isSubFragment: Boolean = false
) : BaseAdapter<MediaStoreUtils.Album>
    (
    mainActivity,
    liveData = albumList,
    sortHelper = StoreAlbumHelper(),
    naturalOrderHelper = null,
    initialSortType = Sorter.Type.ByTitleAscending,
    pluralStr = R.plurals.albums,
    ownsView = ownsView,
    defaultLayoutType = LayoutType.GRID,
    isSubFragment = isSubFragment
) {

    private val libraryViewModel: LibraryViewModel by mainActivity.viewModels()

    constructor(
        mainActivity: MainActivity,
        albumList: List<MediaStoreUtils.Album>,
        isSubFragment: Boolean = false
    )
            : this(mainActivity, null, false, isSubFragment = isSubFragment) {
        updateList(albumList, now = true, false)
    }

    override fun virtualTitleOf(item: MediaStoreUtils.Album): String {
        return context.getString(R.string.unknown_album)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (layoutType == LayoutType.GRID) {
            val item = list[position]
            holder.title.text = titleOf(item) ?: virtualTitleOf(item)
            holder.subTitle.text = subTitleOf(item)
            Glide
                .with(holder.songCover.context)
                .load(coverOf(item))
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(defaultCover)
                .into(holder.songCover)
            holder.itemView.setOnClickListener { onClick(item) }
            holder.itemView.setOnLongClickListener {
                val popupMenu = PopupMenu(it.context, it)
                onMenu(item, popupMenu)
                popupMenu.show()
                true
            }
        } else {
            val item = list[position]
            holder.title.text = titleOf(item) ?: virtualTitleOf(item)
            holder.subTitle.text = subTitleOf(item)
            Glide
                .with(holder.songCover.context)
                .load(coverOf(item))
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(defaultCover)
                .into(holder.songCover)
            holder.itemView.setOnClickListener { onClick(item) }
            holder.moreButton.setOnClickListener {
                val popupMenu = PopupMenu(it.context, it)
                onMenu(item, popupMenu)
                popupMenu.show()
            }
        }
    }

    override fun onClick(item: MediaStoreUtils.Album) {
        mainActivity.startFragment(
            GeneralSubFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt("Position", item.let {
                            if (ownsView) toRawPos(it) else {
                                libraryViewModel.albumItemList.value!!.indexOf(it)
                            }
                        })
                        putInt("Item", R.id.album)
                    }
            },
        )
    }

    override fun onMenu(item: MediaStoreUtils.Album, popupMenu: PopupMenu) {
        popupMenu.inflate(R.menu.more_menu_less)

        popupMenu.setOnMenuItemClickListener { it1 ->
            when (it1.itemId) {
                R.id.play_next -> {
                    val mediaController = mainActivity.getPlayer()
                    mediaController.addMediaItems(
                        mediaController.currentMediaItemIndex + 1,
                        item.songList,
                    )
                }
            }
            true
        }
    }

    class StoreAlbumHelper : StoreItemHelper<MediaStoreUtils.Album>(
        setOf(
            Sorter.Type.ByTitleDescending, Sorter.Type.ByTitleAscending,
            Sorter.Type.ByArtistDescending, Sorter.Type.ByArtistAscending,
            Sorter.Type.BySizeDescending, Sorter.Type.BySizeAscending
        )
    ) {
        override fun getArtist(item: MediaStoreUtils.Album): String? {
            return item.artist
        }
    }
}