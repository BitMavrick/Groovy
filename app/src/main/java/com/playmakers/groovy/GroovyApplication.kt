package com.playmakers.groovy

import android.app.Application
import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.getMusic

class GroovyApplication : Application() {
    lateinit var musicList: List<Music>
    override fun onCreate() {
        super.onCreate()
        musicList = getMusic(this)
    }
}
