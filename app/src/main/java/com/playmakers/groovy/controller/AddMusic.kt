package com.playmakers.groovy.controller

import com.playmakers.groovy.domain.model.PlaybackControl
import com.playmakers.groovy.domain.model.RoomMusic
import javax.inject.Inject

class AddMusic  @Inject constructor(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(musics: List<RoomMusic>){
        playbackControl.addMediaItems(musics)
    }
}