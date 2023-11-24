package com.playmakers.groovy.domain.model

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class Music (
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val source: String,
    val image: String,
    val imagePath: Uri?,
    val actualImage : ImageBitmap?
)