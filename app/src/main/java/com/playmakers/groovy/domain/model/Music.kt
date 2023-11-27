package com.playmakers.groovy.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class Music (
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val source: String,
    val image: String,
    val imagePath: Uri?,
    val actualImage : Bitmap?
)