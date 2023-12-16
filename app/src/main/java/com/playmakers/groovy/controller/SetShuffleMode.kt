package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl
import javax.inject.Inject

class SetShuffleMode @Inject constructor(
    private val playbackControl: PlaybackControl
){
    operator fun invoke(shuffleEnable : Boolean){
        playbackControl.setShuffleModeEnabled(shuffleEnable)
    }
}