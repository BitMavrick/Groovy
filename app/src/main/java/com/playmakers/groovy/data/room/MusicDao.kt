package com.playmakers.groovy.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.playmakers.groovy.domain.model.Music
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(music: Music)

    @Update
    suspend fun update(music: Music)

    @Query("DELETE from `musics`")
    suspend fun delete()

    @Query("SELECT * FROM musics WHERE id = :id")
    fun getMusic(id: Int): Flow<Music>

    @Query("SELECT * FROM musics ORDER BY title ASC")
    fun getAllMusics(): Flow<List<Music>>
}