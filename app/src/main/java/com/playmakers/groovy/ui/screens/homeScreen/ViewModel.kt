package com.playmakers.groovy.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import com.playmakers.groovy.data.Music

class MusicViewModel : ViewModel() {
    private val _musicList = mutableListOf<Music>()

    val musicList: List<Music>
        get() = _musicList

    fun updateMusicList(newMusicList: List<Music>) {
        _musicList.clear()
        _musicList.addAll(newMusicList)
    }
}