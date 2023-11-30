package com.playmakers.groovy.domain.repository

import com.playmakers.groovy.data.room.RoomMusic

interface MusicRepository {
    suspend fun getMusicFiles(): List<RoomMusic>
    suspend fun quickFetchMusicFiles() : List<RoomMusic>
}