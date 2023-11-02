package com.playmakers.groovy

import android.app.Application
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.service.MusicPlayBackControl
import com.playmakers.groovy.service.getMusic

class GroovyApplication : Application() {
    lateinit var musicList: List<Music>
    lateinit var playBackControl: PlayBackControl
    override fun onCreate() {
        super.onCreate()
        musicList = getMusic(this)
        playBackControl = MusicPlayBackControl(this)
    }
}
