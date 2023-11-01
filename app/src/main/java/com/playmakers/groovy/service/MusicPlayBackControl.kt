package com.playmakers.groovy.service

import android.content.Context
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.model.PlayerState

class MusicPlayBackControl(context: Context) : PlayBackControl {
    override var mediaControllerCallback: ((playerState: PlayerState, currentMusic: Music?, currentPosition: Long, totalDuration: Long, isShuffleEnabled: Boolean, isRepeatOneEnabled: Boolean) -> Unit)?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun addMediaItems(musics: List<Music>) {
        TODO("Not yet implemented")
    }

    override fun play(mediaItemIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun seekTo(position: Long) {
        TODO("Not yet implemented")
    }

    override fun skipNext() {
        TODO("Not yet implemented")
    }

    override fun skipPrevious() {
        TODO("Not yet implemented")
    }

    override fun setShuffleModeEnabled(isEnabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setRepeatOneEnabled(isEnabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCurrentPosition(): Long {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }

}