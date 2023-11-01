package com.playmakers.groovy.control

import com.playmakers.groovy.model.PlayBackControl

class SkipNextMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(){
        playBackControl.skipNext()
    }
}