package com.playmakers.groovy.data

import android.content.Context
import com.playmakers.groovy.domain.repository.MusicRepository

interface MusicContainer {
    val musicRepository : MusicRepository
}

class MusicDataContainer(private val context: Context): MusicContainer{
    override val musicRepository: MusicRepository
        get() = TODO("Not yet implemented")
}