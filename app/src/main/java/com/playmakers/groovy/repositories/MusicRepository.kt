package com.playmakers.groovy.repositories

import com.playmakers.groovy.data.Music

interface MusicRepository {
    fun getMusicList() : List<Music>
}