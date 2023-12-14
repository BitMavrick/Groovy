package com.playmakers.groovy.controller

import android.util.Log
import com.playmakers.groovy.domain.model.PlaybackControl
import com.playmakers.groovy.domain.model.RoomMusic
import javax.inject.Inject

class AddMusic  @Inject constructor(
    private val playbackControl: PlaybackControl
) {
    operator fun invoke(musics: List<RoomMusic>){
        Log.d("Groovy Music", "Total Music added: ${musics.size}")
        playbackControl.addMediaItems(musics)
    }
}