package com.playmakers.groovy

import android.app.Application
import android.content.Intent
import android.util.Log
import com.playmakers.groovy.control.AddMusic
import com.playmakers.groovy.control.DestroyMusicPlayBackControl
import com.playmakers.groovy.control.GetMediaPosition
import com.playmakers.groovy.control.PauseMusic
import com.playmakers.groovy.control.PlayMusic
import com.playmakers.groovy.control.ResumeMusic
import com.playmakers.groovy.control.SetMediaControlCallBack
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayBackControl
import com.playmakers.groovy.service.MusicPlayBackControl
import com.playmakers.groovy.service.MusicPlayBackService
import com.playmakers.groovy.service.getMusic

class GroovyApplication : Application() {
    private lateinit var playBackControl: PlayBackControl

    lateinit var musicList: List<Music>
    lateinit var addMusic: AddMusic
    lateinit var playMusic: PlayMusic
    lateinit var resumeMusic: ResumeMusic
    lateinit var pauseMusic: PauseMusic
    lateinit var setMediaControlCallBack: SetMediaControlCallBack
    lateinit var currentMediaPosition: GetMediaPosition
    lateinit var destroyMusic: DestroyMusicPlayBackControl

    override fun onCreate() {
        super.onCreate()
        playBackControl = MusicPlayBackControl(this)

        musicList = getMusic(this)
        addMusic = AddMusic(playBackControl)
        playMusic = PlayMusic(playBackControl)
        resumeMusic = ResumeMusic(playBackControl)
        pauseMusic = PauseMusic(playBackControl)
        setMediaControlCallBack = SetMediaControlCallBack(playBackControl)
        currentMediaPosition = GetMediaPosition(playBackControl)
        destroyMusic = DestroyMusicPlayBackControl(playBackControl)
    }
}
