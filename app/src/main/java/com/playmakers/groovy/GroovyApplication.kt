package com.playmakers.groovy

import android.app.Application
import com.playmakers.groovy.control.PlayMusic
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.service.MusicPlayBackControl
import com.playmakers.groovy.service.getMusic

class GroovyApplication : Application() {
    private lateinit var playBackControl: PlayBackControl

    lateinit var musicList: List<Music>
    lateinit var playMusic: PlayMusic

    override fun onCreate() {
        super.onCreate()
        playBackControl = MusicPlayBackControl(this)

        musicList = getMusic(this)
        playMusic = PlayMusic(playBackControl)
    }
}
