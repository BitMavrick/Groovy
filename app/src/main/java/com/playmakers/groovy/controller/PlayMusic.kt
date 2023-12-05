package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl

class PlayMusic(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(mediaItemsIndex: Int){
        playbackControl.play(mediaItemsIndex)
    }
}