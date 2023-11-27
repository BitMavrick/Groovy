package com.playmakers.groovy.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RoomMusic::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract class musicDao() : MusicDao

    companion object {
        @Volatile
        private var Instance : MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDatabase::class.java, "music_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}