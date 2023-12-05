package com.playmakers.groovy.domain.repository

import com.playmakers.groovy.domain.model.RoomMusic
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun getMusicFiles(): List<RoomMusic>
    suspend fun getMusicsFlow(): Flow<RoomMusic>
    suspend fun quickFetchMusicFiles() : List<RoomMusic>
}