package com.playmakers.groovy.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class Music (
    val id: String,
    val title: String,
    val artist: String,
    val source: String,
    val image: String
)

// TODO: >>>>>>>>>> The toMusic function have some serious integration bug, we have to fix this <<<<<<<<<<
fun MediaItem.toMusic() =
    Music(
        id = mediaId,
        title = mediaMetadata.title.toString(),
        artist = mediaMetadata.artist.toString(),
        source = mediaId,
        image = mediaMetadata.artworkUri.toString()
    )