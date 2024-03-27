package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.adapter.AlbumAdapter
import com.bitmavrick.groovy.ui.adapter.ArtistAdapter
import com.bitmavrick.groovy.ui.adapter.GenreAdapter
import com.bitmavrick.groovy.ui.adapter.SongAdapter
import com.bitmavrick.groovy.ui.adapter.DateAdapter
import com.bitmavrick.groovy.ui.adapter.FolderAdapter
import com.bitmavrick.groovy.ui.adapter.PlaylistAdapter
import com.bitmavrick.groovy.ui.adapter.DetailedFolderAdapter
import com.bitmavrick.groovy.ui.viewmodel.LibraryViewModel
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import me.zhanghai.android.fastscroll.PopupTextProvider

class AdapterFragment : BaseFragment(null) {
    private val libraryViewModel: LibraryViewModel by activityViewModels()

    private lateinit var adapter: BaseInterface<*>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerview)
        adapter = createAdapter(requireActivity() as MainActivity, libraryViewModel)
        recyclerView.adapter = adapter.concatAdapter

        FastScrollerBuilder(recyclerView).apply {
            setPopupTextProvider(adapter)
            useMd2Style()
            setTrackDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_transparent
                )!!
            )
            build()
        }
        return rootView
    }

    private fun createAdapter(m: MainActivity, v: LibraryViewModel): BaseInterface<*> {
        return when (arguments?.getInt("ID", -1)) {
            R.id.songs -> SongAdapter(m, v.mediaItemList, true, null, true)
            R.id.albums -> AlbumAdapter(m, v.albumItemList)
            R.id.artists -> ArtistAdapter(m, v.artistItemList, v.albumArtistItemList)
            R.id.genres -> GenreAdapter(m, v.genreItemList)
            R.id.dates -> DateAdapter(m, v.dateItemList)
            R.id.folders -> FolderAdapter(m, v.folderStructure)
            R.id.detailed_folders -> DetailedFolderAdapter(m, v.shallowFolderStructure)
            R.id.playlists -> PlaylistAdapter(m, v.playlistList)
            -1, null -> throw IllegalArgumentException("unset ID value")
            else -> throw IllegalArgumentException("invalid ID value")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.concatAdapter.adapters.forEach {
            it.onDetachedFromRecyclerView(recyclerView)
        }
    }

    abstract class BaseInterface<T : RecyclerView.ViewHolder>
        : RecyclerView.Adapter<T>(), PopupTextProvider {
        abstract val concatAdapter: ConcatAdapter
    }
}
