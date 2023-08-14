@file:Suppress("DEPRECATION")

package com.playmakers.groovy.ui.screens.homeScreen

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class MusicPlayer(private val context: Context) {
    private val player = SimpleExoPlayer.Builder(context).build()
    fun playMusic(uri: Uri) {
        player.stop()
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun stopMusic() {
        player.stop()
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

    fun isPlaying(): Boolean {
        return player.isPlaying
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun getCurrentPosition(): Long {
        return player.currentPosition
    }

    fun getDuration(): Long {
        return player.duration
    }

    fun setVolume(volume: Float) {
        player.volume = volume
    }

    fun setPlaybackSpeed(speed: Float) {
        player.playbackParameters = player.playbackParameters.withSpeed(speed)
    }

    fun setRepeatMode(repeatMode: Int) {
        player.repeatMode = repeatMode
    }

    fun setShuffleModeEnabled(shuffleModeEnabled: Boolean) {
        player.shuffleModeEnabled = shuffleModeEnabled
    }

    fun setPlayWhenReady(playWhenReady: Boolean) {
        player.playWhenReady = playWhenReady
    }

    fun getPlayWhenReady(): Boolean {
        return player.playWhenReady
    }

    fun getPlaybackState(): Int {
        return player.playbackState
    }

    fun getBufferedPosition(): Long {
        return player.bufferedPosition
    }

    fun getBufferedPercentage(): Int {
        return player.bufferedPercentage
    }

    fun getAudioSessionId(): Int {
        return player.audioSessionId
    }
}