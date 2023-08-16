package com.playmakers.groovy.repositories

import com.playmakers.groovy.data.Music
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor() : MusicRepository {
    override fun getMusicList(): List<Music> {
        TODO("Not yet implemented")
    }
}