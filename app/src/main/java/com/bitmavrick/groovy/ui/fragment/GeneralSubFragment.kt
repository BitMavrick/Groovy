package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.core.util.MediaStoreUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.adapter.SongAdapter
import com.bitmavrick.groovy.ui.adapter.Sorter
import com.bitmavrick.groovy.ui.viewmodel.LibraryViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.color.MaterialColors
import me.zhanghai.android.fastscroll.FastScrollerBuilder

@androidx.annotation.OptIn(UnstableApi::class)
class GeneralSubFragment : BaseFragment(true) {
    private val libraryViewModel: LibraryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        lateinit var itemList: List<MediaItem>

        val rootView = inflater.inflate(R.layout.fragment_general_sub, container, false)
        val appBarLayout = rootView.findViewById<AppBarLayout>(R.id.appbarlayout)
        val topAppBar = rootView.findViewById<MaterialToolbar>(R.id.topAppBar)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerview)

        val bundle = requireArguments()
        val itemType = bundle.getInt("Item")
        val position = bundle.getInt("Position")

        val title: String?

        var helper: Sorter.NaturalOrderHelper<MediaItem>? = null

        var isTrackDiscNumAvailable = false

        val processColor = ColorUtils.getColor(
            MaterialColors.getColor(
                topAppBar,
                android.R.attr.colorBackground
            ),
            ColorUtils.ColorType.COLOR_BACKGROUND,
            requireContext(),
            true
        )
        topAppBar.setBackgroundColor(processColor)
        appBarLayout.setBackgroundColor(processColor)

        when (itemType) {
            R.id.album -> {
                val item = libraryViewModel.albumItemList.value!![position]
                title = item.title ?: requireContext().getString(R.string.unknown_album)
                itemList = item.songList
                helper =
                    Sorter.NaturalOrderHelper {
                        it.mediaMetadata.trackNumber?.plus(
                            it.mediaMetadata.discNumber?.times(1000) ?: 0
                        ) ?: 0
                    }
                isTrackDiscNumAvailable = true
            }


            R.id.genre -> {
                val item = libraryViewModel.genreItemList.value!![position]
                title = item.title ?: requireContext().getString(R.string.unknown_genre)
                itemList = item.songList
            }

            R.id.year -> {
                val item = libraryViewModel.dateItemList.value!![position]
                title = item.title ?: requireContext().getString(R.string.unknown_year)
                itemList = item.songList
            }

            R.id.playlist -> {
                val item = libraryViewModel.playlistList.value!![position]
                title = if (item is MediaStoreUtils.RecentlyAdded) {
                    requireContext().getString(R.string.recently_added)
                } else {
                    item.title ?: requireContext().getString(R.string.unknown_playlist)
                }
                itemList = item.songList
                helper = Sorter.NaturalOrderHelper { itemList.indexOf(it) }
            }

            else -> throw IllegalArgumentException()
        }

        topAppBar.title = title

        val songAdapter =
            SongAdapter(
                requireActivity() as MainActivity,
                itemList,
                true,
                helper,
                true,
                isTrackDiscNumAvailable,
                true
            )

        recyclerView.adapter = songAdapter.concatAdapter

        FastScrollerBuilder(recyclerView).apply {
            useMd2Style()
            setPopupTextProvider(songAdapter)
            setTrackDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_transparent
                )!!
            )
            build()
        }

        topAppBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return rootView
    }
}
