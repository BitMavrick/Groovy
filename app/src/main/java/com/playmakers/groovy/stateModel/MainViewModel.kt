package com.playmakers.groovy.stateModel

import androidx.lifecycle.ViewModel
import com.playmakers.groovy.model.Music

class MainViewModel(private val musicList: List<Music>) : ViewModel(){
    fun getMusic() : List<Music> {
        return musicList
    }
}