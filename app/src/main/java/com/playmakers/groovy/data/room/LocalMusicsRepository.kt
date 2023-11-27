package com.playmakers.groovy.data.room

import com.playmakers.groovy.data.MusicsRepository
import kotlinx.coroutines.flow.Flow

class LocalMusicsRepository(private val musicDao: MusicDao) : MusicsRepository{
    override fun getAllMusicsStream(): Flow<List<RoomMusic>> {
        return musicDao.getAllMusics()
    }

    override fun getMusicStream(id: Int): Flow<RoomMusic> {
        return musicDao.getMusic(id)
    }

    override suspend fun insertMusic(music: RoomMusic) {
        return musicDao.insert(music)
    }

    override suspend fun insertAllMusic(musics: List<RoomMusic>){
        return musicDao.insertAll(musics)
    }

    override suspend fun clearMusic() {
        return musicDao.delete()
    }
}