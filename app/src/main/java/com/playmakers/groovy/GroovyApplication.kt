package com.playmakers.groovy

import android.app.Application
import com.playmakers.groovy.control.AddMusic
import com.playmakers.groovy.control.PauseMusic
import com.playmakers.groovy.control.PlayMusic
import com.playmakers.groovy.control.ResumeMusic
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.service.MusicPlayBackControl
import com.playmakers.groovy.service.getMusic

class GroovyApplication : Application() {
    private lateinit var playBackControl: PlayBackControl

    lateinit var musicList: List<Music>
    lateinit var addMusic: AddMusic
    lateinit var playMusic: PlayMusic
    lateinit var resumeMusic: ResumeMusic
    lateinit var pauseMusic: PauseMusic

    override fun onCreate() {
        super.onCreate()
        playBackControl = MusicPlayBackControl(this)

        musicList = getMusic(this)
        addMusic = AddMusic(playBackControl)
        playMusic = PlayMusic(playBackControl)
        resumeMusic = ResumeMusic(playBackControl)
        pauseMusic = PauseMusic(playBackControl)
    }
}
