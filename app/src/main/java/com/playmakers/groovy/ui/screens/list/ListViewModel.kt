package com.playmakers.groovy.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.domain.model.Music
import com.playmakers.groovy.domain.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (
    private val musicRepository: MusicRepository
): ViewModel() {
    private var musicList: List<Music> = emptyList()

    init {
        viewModelScope.launch {
            musicList = musicRepository.getMusicFiles()
        }
    }
}