package com.bitmavrick.groovy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bitmavrick.groovy.R
import com.bitmavrick.groovy.core.closeKeyboard
import com.bitmavrick.groovy.core.showSoftKeyboard
import com.bitmavrick.groovy.core.util.ColorUtils
import com.bitmavrick.groovy.ui.MainActivity
import com.bitmavrick.groovy.ui.adapter.SongAdapter
import com.bitmavrick.groovy.ui.viewmodel.LibraryViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.zhanghai.android.fastscroll.FastScrollerBuilder

class SearchFragment : BaseFragment(false) {
    private val libraryViewModel: LibraryViewModel by activityViewModels()
    private val filteredList: MutableList<MediaItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        val editText = rootView.findViewById<EditText>(R.id.edit_text)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerview)
        val songAdapter =
            SongAdapter(requireActivity() as MainActivity, listOf(), false, null, false)
        val returnButton = rootView.findViewById<MaterialButton>(R.id.return_button)
        val appBarLayout = rootView.findViewById<AppBarLayout>(R.id.appbarlayout)

        val processColor = ColorUtils.getColor(
            MaterialColors.getColor(
                rootView,
                android.R.attr.colorBackground
            ),
            ColorUtils.ColorType.COLOR_BACKGROUND,
            requireContext(),
            true
        )

        rootView.setBackgroundColor(processColor)
        appBarLayout.setBackgroundColor(processColor)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = songAdapter.concatAdapter

        FastScrollerBuilder(recyclerView).apply {
            setPopupTextProvider(songAdapter)
            useMd2Style()
            setTrackDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_transparent
                )!!
            )
            build()
        }

        editText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                songAdapter.updateList(listOf(), now = false, true)
            } else {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                    filteredList.clear()
                    libraryViewModel.mediaItemList.value?.filter {
                        val isMatchingTitle = it.mediaMetadata.title?.contains(text, true) ?: false
                        val isMatchingAlbum =
                            it.mediaMetadata.albumTitle?.contains(text, true) ?: false
                        val isMatchingArtist =
                            it.mediaMetadata.artist?.contains(text, true) ?: false
                        isMatchingTitle || isMatchingAlbum || isMatchingArtist
                    }?.let {
                        filteredList.addAll(
                            it
                        )
                    }
                    withContext(Dispatchers.Main) {
                        songAdapter.updateList(filteredList, now = false, true)
                    }
                }
            }
        }

        returnButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return rootView
    }

    override fun onPause() {
        super.onPause()
        requireActivity().closeKeyboard()
    }

    override fun onResume() {
        super.onResume()
        val editText = requireView().findViewById<EditText>(R.id.edit_text)
        requireView().post {
            editText.showSoftKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwner.lifecycleScope.cancel()
    }

}
