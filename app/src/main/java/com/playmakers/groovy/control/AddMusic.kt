package com.playmakers.groovy.control

import android.util.Log
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl

class AddMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(musics: List<Music>) {
        playBackControl.addMediaItems(musics)
    }
}