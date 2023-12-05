package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl
import javax.inject.Inject

class PlayMusic @Inject constructor(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(mediaItemsIndex: Int){
        playbackControl.play(mediaItemsIndex)
    }
}