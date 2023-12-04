package com.playmakers.groovy.data.repository

import android.content.Context
import com.playmakers.groovy.domain.model.PlaybackControl
import com.playmakers.groovy.domain.model.PlayerState
import com.playmakers.groovy.domain.model.RoomMusic

class PlaybackControlImpl(context: Context) : PlaybackControl {
    override var mediaControllerCallback: ((playerState: PlayerState, currentMusic: RoomMusic?, currentPosition: Long, totalDuration: Long, isShuffleEnabled: Boolean, isRepeatOneEnabled: Boolean) -> Unit)?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun addMediaItems(musics: List<RoomMusic>) {
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