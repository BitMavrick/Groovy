package com.playmakers.groovy.control

import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.model.PlayerState

class SetMediaControlCallBack(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(
        callback: (
            playerState: PlayerState,
            currentMusic: Music?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOneEnabled: Boolean
        ) -> Unit
    ){
        playBackControl.mediaControllerCallback = callback
    }
}