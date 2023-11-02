package com.playmakers.groovy.stateModel

import androidx.lifecycle.ViewModel
import com.playmakers.groovy.control.PlayMusic
import com.playmakers.groovy.model.Music

class MainViewModel(
    private val musicList: List<Music>,

    private val playMusic: PlayMusic,
) : ViewModel() {

    fun getMusic() : List<Music> {
        return musicList
    }

    fun onEvent(event: MainEvent){
//        when (event) {
//            MainEvent.PlayMusic -> playMusic()
//
//
//        }
    }

    private fun playMusic() {

    }

    private fun resumeMusic() {
        //resumeMusicUseCase()
    }

    private fun pauseMusic() {
        //pauseMusicUseCase()
    }
}