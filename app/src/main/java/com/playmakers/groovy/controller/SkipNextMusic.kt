package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl

class SkipNextMusic(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(){
        playbackControl.skipNext()
    }
}