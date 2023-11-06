package com.playmakers.groovy.control

import android.util.Log
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl

class AddMusic(
    private val playBackControl: PlayBackControl
) {
    operator fun invoke(musics: List<Music>) {
        Log.d("Debug", "HERE INSIDE THE ADD MUSIC -- LIST INVOKE -- SIZE: ${musics.size}")

//        for(music in musics){
//            Log.d("Debug", "MUSIC -- ID: ${music.id} -- TITLE: ${music.title} --")
//        }

        playBackControl.addMediaItems(musics)
    }
}