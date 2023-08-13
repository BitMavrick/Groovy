package com.playmakers.groovy.ui.screens.homeScreen

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.data.dummyMusicList

class MusicViewModel : ViewModel() {

    private val _music = dummyMusicList.toMutableStateList()
    val music : List<Music>
        get() = _music
}