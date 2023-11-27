package com.playmakers.groovy.data.room

import com.playmakers.groovy.data.MusicsRepository
import com.playmakers.groovy.domain.model.Music
import kotlinx.coroutines.flow.Flow

class LocalMusicsRepository(private val musicDao: MusicDao) : MusicsRepository{
    override fun getAllMusicsStream(): Flow<List<Music>> {
        return musicDao.getAllMusics()
    }

    override fun getMusicStream(id: Int): Flow<Music> {
        return musicDao.getMusic(id)
    }

    override suspend fun insertMusic(music: Music) {
        return musicDao.insert(music)
    }

    override suspend fun clearMusic() {
        return musicDao.delete()
    }
}