package com.playmakers.groovy.control

import android.util.Log
import com.playmakers.groovy.model.PlayBackControl

class PlayMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(mediaItemsIndex: Int){
        Log.d("Debug", "IN THE INVOKE FUNCTION $mediaItemsIndex")
        playBackControl.play(mediaItemsIndex)
    }
}