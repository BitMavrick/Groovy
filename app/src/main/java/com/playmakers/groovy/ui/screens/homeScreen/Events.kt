@file:Suppress("DEPRECATION")

package com.playmakers.groovy.ui.screens.homeScreen

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class MusicPlayer(private val context: Context) {
    private val player = SimpleExoPlayer.Builder(context).build()
    fun playMusic(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
    fun pauseMusic() {
        player.pause()
    }
    fun release() {
        player.release()
    }

    fun resumeMusic() {
        player.play()
    }
}