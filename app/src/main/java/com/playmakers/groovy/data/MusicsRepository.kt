package com.playmakers.groovy.data

import com.playmakers.groovy.data.room.RoomMusic
import kotlinx.coroutines.flow.Flow

interface MusicsRepository {
    fun getAllMusicsStream(): Flow<List<RoomMusic>>

    fun getMusicStream(id: Int): Flow<RoomMusic>

    suspend fun insertMusic(music: RoomMusic)

    suspend fun insertAllMusic(musics: List<RoomMusic>)

    suspend fun clearMusic()
}