package com.playmakers.groovy.domain.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Musics")
data class RoomMusic (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val artist: String,
    val album: String,
    val source: String,
    val image: String,
    val imagePath: Uri?,
    val actualImage : Bitmap? = null
)