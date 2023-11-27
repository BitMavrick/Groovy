package com.playmakers.groovy.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM musics ORDER BY title ASC")
    fun getAllMusics(): Flow<List<RoomMusic>>
}