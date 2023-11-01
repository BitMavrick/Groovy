package com.playmakers.groovy.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class Music (
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val path: String,
    val contentUri: Uri?,
)

// TODO: >>>>>>>>>> The toMusic function have some serious integration bug, we have to fix this <<<<<<<<<<
fun MediaItem.toMusic() =
    Music(
        id = mediaId,
        title = mediaMetadata.title.toString(),
        artist = mediaMetadata.artist.toString(),
        album = mediaMetadata.albumTitle.toString(),
        duration = 0L,
        path = "No Path",
        contentUri = null
    )