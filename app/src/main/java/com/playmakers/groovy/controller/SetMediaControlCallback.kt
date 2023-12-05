package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl
import com.playmakers.groovy.domain.model.PlayerState
import com.playmakers.groovy.domain.model.RoomMusic

class SetMediaControlCallback(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(
        callback: (
            playerState: PlayerState,
            currentMusic: RoomMusic?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOneEnabled: Boolean
        ) -> Unit
    ){
        playbackControl.mediaControllerCallback = callback
    }

}