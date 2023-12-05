package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl

class GetMusicPosition(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke() = playbackControl.getCurrentPosition()
}