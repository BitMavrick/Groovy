package com.playmakers.groovy.control

import com.playmakers.groovy.model.PlayBackControl

class PlayMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(mediaItemsIndex: Int){
        playBackControl.play(mediaItemsIndex)
    }
}