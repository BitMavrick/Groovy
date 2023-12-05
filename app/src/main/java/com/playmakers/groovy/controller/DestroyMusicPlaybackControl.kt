package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl

class DestroyMusicPlaybackControl(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke() {
        playbackControl.destroy()
    }
}