package com.playmakers.groovy.data.room

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
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
    val actualImage : ImageBitmap?
)