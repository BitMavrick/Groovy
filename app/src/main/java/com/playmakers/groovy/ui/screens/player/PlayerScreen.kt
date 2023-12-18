package com.playmakers.groovy.ui.screens.player

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.playmakers.groovy.R
import kotlin.math.roundToInt

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
){
    val playerUiState = playerViewModel.playerUiState
    val playerEvent = playerViewModel::onEvent

    val currentMusicSource = playerUiState.currentMusic?.source?.toUri()
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(currentMusicSource) {
        currentMusicSource?.let { source ->
            val bitmap = getAlbumArt(context, source, 100, 100).asImageBitmap()
            imageBitmap = bitmap
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            if(playerUiState.isPlayerExpanded){
                Modifier
                    .animateContentSize()
                    .fillMaxSize()
            }else{
                Modifier
                    .clickable {
                        playerEvent(PlayerEvent.PlayerExpand)
                    }
                    .animateContentSize()
                    .fillMaxWidth()
                    .height(86.dp)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            }
        ) {
            if(playerUiState.isPlayerExpanded) {
                BackHandler {
                    playerEvent(PlayerEvent.PlayerCollapse)
                }
            }else{
                Box(
                    Modifier.clip(RoundedCornerShape(5.dp))
                ){
                    if (imageBitmap != null) {
                        Image(
                            bitmap = imageBitmap!!,
                            contentDescription = "Album art",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.aspectRatio(1f)
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    playerUiState.currentMusic?.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    playerUiState.currentMusic?.artist?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}


fun getAlbumArt(context: Context, uri: Uri, targetWidth: Int, targetHeight: Int): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture

    return if(data != null){
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)
        options.inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(data, 0, data.size, options)

    } else {
        BitmapFactory.decodeResource(context.resources, R.drawable.default_album_art)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}