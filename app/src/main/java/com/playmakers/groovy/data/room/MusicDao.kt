package com.playmakers.groovy.data.room

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.playmakers.groovy.domain.model.RoomMusic
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(music: RoomMusic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musics: List<RoomMusic>)

    @Query("DELETE from `musics`")
    suspend fun delete()

    @Query("SELECT * FROM musics WHERE id = :id")
    fun getMusic(id: Int): Flow<RoomMusic>

    @Query("SELECT * FROM musics WHERE source = :source")
    fun getMusicBySource(source: String): RoomMusic

    @Query("UPDATE Musics SET actualImage = :image WHERE id = :musicId")
    suspend fun updateActualImage(musicId: Int, image: Bitmap)

    @Query("SELECT * FROM musics ORDER BY title ASC")
    fun getAllMusics(): Flow<List<RoomMusic>>

    @Query("SELECT * FROM musics ORDER BY title ASC")
    fun getAllMusicsAsList(): List<RoomMusic>

    @Query("SELECT COUNT(*) FROM musics")
    fun getTableSize(): Int
}