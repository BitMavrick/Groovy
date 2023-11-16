package com.playmakers.groovy.domain.repository

import com.playmakers.groovy.domain.model.Music

interface MusicRepository {
    suspend fun getMusicFiles(): List<Music>
}