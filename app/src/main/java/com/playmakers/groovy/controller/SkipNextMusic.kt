package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl
import javax.inject.Inject

class SkipNextMusic @Inject constructor(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(){
        playbackControl.skipNext()
    }
}