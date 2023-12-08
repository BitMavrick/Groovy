package com.playmakers.groovy.domain.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Musics")
data class RoomMusic (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val source: String,
    val image: String,
    val imagePath: Uri?,
    val actualImage : Bitmap? = null
)

fun MediaItem.toMusic() =
    RoomMusic(
        id = mediaId,
        title = mediaMetadata.title.toString(),
        artist = mediaMetadata.artist.toString(),
        album = mediaMetadata.albumTitle.toString(),
        source = mediaId,
        image = mediaMetadata.artworkUri.toString(),
        imagePath = null,
        actualImage = null
    )