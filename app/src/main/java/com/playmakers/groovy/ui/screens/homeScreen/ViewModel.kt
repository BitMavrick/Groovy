package com.playmakers.groovy.ui.screens.homeScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {

    private lateinit var musicPlayer: MusicPlayer

    fun initMusicPlayer(context: Context) {
        musicPlayer = MusicPlayer(context)
    }

    fun playMusic(uri: Uri) {
        musicPlayer.playMusic(uri)
    }

    fun stopMusic() {
        musicPlayer.stopMusic()
    }

    fun pauseMusic() {
        musicPlayer.pauseMusic()
    }

    fun resumeMusic() {
        musicPlayer.resumeMusic()
    }

    fun isPlaying(): Boolean {
        return musicPlayer.isPlaying()
    }

    // Release the player when the ViewModel is no longer used
    override fun onCleared() {
        super.onCleared()
        musicPlayer.release()
    }

    fun release(){
        musicPlayer.release()
    }
}