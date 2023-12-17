package com.playmakers.groovy.data

import android.content.Context
import com.playmakers.groovy.data.room.LocalMusicsRepository
import com.playmakers.groovy.data.room.MusicDatabase

interface MusicContainer {
    val musicsRepository : MusicsRepository
}
class MusicDataContainer(private val context: Context) : MusicContainer {
    override val musicsRepository: MusicsRepository by lazy {
        LocalMusicsRepository(MusicDatabase.getDatabase(context).musicDao())
    }
}