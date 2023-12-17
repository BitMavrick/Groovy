package com.playmakers.groovy.data

import android.graphics.Bitmap
import com.playmakers.groovy.domain.model.RoomMusic
import kotlinx.coroutines.flow.Flow

interface MusicsRepository {
    fun getAllMusicsStream(): Flow<List<RoomMusic>>

    fun getAllMusicsStreamAsList(): List<RoomMusic>

    fun getMusicStream(id: Int): Flow<RoomMusic>

    fun getMusicBySource(source: String) : RoomMusic

    suspend fun insertMusic(music: RoomMusic)

    suspend fun insertAllMusic(musics: List<RoomMusic>)

    suspend fun updateActualImage(musicId: Int, image: Bitmap)

    fun getTableSize() : Int

    suspend fun clearMusic()
}