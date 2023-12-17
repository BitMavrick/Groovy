package com.playmakers.groovy.data.room

import android.graphics.Bitmap
import com.playmakers.groovy.data.MusicsRepository
import com.playmakers.groovy.domain.model.RoomMusic
import kotlinx.coroutines.flow.Flow

class LocalMusicsRepository(private val musicDao: MusicDao) : MusicsRepository{
    override fun getAllMusicsStream(): Flow<List<RoomMusic>> {
        return musicDao.getAllMusics()
    }

    override fun getAllMusicsStreamAsList(): List<RoomMusic> {
        return musicDao.getAllMusicsAsList()
    }

    override fun getMusicStream(id: Int): Flow<RoomMusic> {
        return musicDao.getMusic(id)
    }

    override fun getMusicBySource(source: String): RoomMusic {
        return musicDao.getMusicBySource(source)
    }

    override suspend fun insertMusic(music: RoomMusic) {
        return musicDao.insert(music)
    }

    override suspend fun insertAllMusic(musics: List<RoomMusic>){
        return musicDao.insertAll(musics)
    }

    override suspend fun updateActualImage(musicId: Int, image: Bitmap) {
        return musicDao.updateActualImage(musicId, image)
    }

    override fun getTableSize(): Int {
        return musicDao.getTableSize()
    }

    override suspend fun clearMusic() {
        return musicDao.delete()
    }
}