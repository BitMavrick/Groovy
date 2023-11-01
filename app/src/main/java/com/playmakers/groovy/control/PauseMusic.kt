package com.playmakers.groovy.control

import com.playmakers.groovy.model.PlayBackControl

class PauseMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(){
        playBackControl.pause()
    }
}