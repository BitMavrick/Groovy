package com.playmakers.groovy.data

import com.playmakers.groovy.domain.model.Music
import kotlinx.coroutines.flow.Flow

interface MusicsRepository {
    fun getAllMusicsStream(): Flow<List<Music>>

    fun getMusicStream(id: Int): Flow<Music>

    suspend fun insertMusic(music: Music)

    suspend fun clearMusic()
}