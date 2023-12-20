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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.playmakers.groovy.R
import com.playmakers.groovy.domain.model.PlayerState
import kotlin.math.roundToInt

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
){
    val playerUiState = playerViewModel.playerUiState
    val playerEvent = playerViewModel::onEvent
    var isPlayerExpanded by rememberSaveable { mutableStateOf(false) }

    val currentMusicSource = playerUiState.currentMusic?.source?.toUri()
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(currentMusicSource) {
        currentMusicSource?.let { source ->
            val bitmap = getAlbumArt(context, source, 400, 400).asImageBitmap()
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
            if(isPlayerExpanded){
                Modifier
                    .animateContentSize()
                    .fillMaxSize()
            }else{
                Modifier
                    .clickable {
                        isPlayerExpanded = true
                    }
                    .animateContentSize()
                    .fillMaxWidth()
                    .height(86.dp)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            }
        ) {
            if(isPlayerExpanded) {
                BackHandler {
                    isPlayerExpanded = false
                }

                PlayerScreenExpanded(
                    playerViewModel = playerViewModel,
                    albumArt = imageBitmap,
                    onMinimizeCLick = {
                        isPlayerExpanded = false
                    }
                )
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

                Box(
                    modifier = Modifier
                        .weight(3f)
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp)
                ) {
                    Column(
                        Modifier.padding(start = 16.dp)
                    ) {
                        playerUiState.currentMusic?.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        playerUiState.currentMusic?.artist?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Box(modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    if(playerUiState.playerState == PlayerState.PLAYING){
                        IconButton(onClick = {
                            playerEvent(PlayerEvent.PauseMusic)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                    }else if(playerUiState.playerState == PlayerState.PAUSED){
                        IconButton(onClick = {
                            playerEvent(PlayerEvent.ResumeMusic)
                        }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
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

@Composable
fun PlayerScreenExpanded(
    playerViewModel: PlayerViewModel,
    albumArt: ImageBitmap?,
    onMinimizeCLick: () -> Unit
){
    val playerUiState = playerViewModel.playerUiState
    val playerEvent = playerViewModel::onEvent

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                Column(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Groovy Music",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        Modifier.clip(RoundedCornerShape(10.dp))
                    ){
                        if(albumArt != null){
                            Image(
                                bitmap = albumArt,
                                contentDescription = "Album art",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.aspectRatio(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    playerUiState.currentMusic?.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    playerUiState.currentMusic?.artist?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    val progressValue = ( playerUiState.currentPosition.toFloat() / playerUiState.totalDuration)

                    LinearProgressIndicator(
                        progress = { progressValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Text(
                            text = formatDuration(playerUiState.currentPosition),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = formatDuration(playerUiState.totalDuration),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                playerEvent(PlayerEvent.SkipPrevious)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        if(playerUiState.playerState == PlayerState.PLAYING){
                            Card(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .height(60.dp)
                                    .width(60.dp)
                                    .clickable {
                                        playerEvent(PlayerEvent.PauseMusic)
                                    },
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Pause,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }

                        }else if( playerUiState.playerState == PlayerState.PAUSED){
                            Card(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .height(60.dp)
                                    .width(60.dp)
                                    .clickable {
                                        playerEvent(PlayerEvent.ResumeMusic)
                                    },
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = {
                                playerEvent(PlayerEvent.SkipNext)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.RepeatOne,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = {
                            onMinimizeCLick()
                        }
                    ) {
                        Text(
                            text = "Minimize",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

fun formatDuration(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val hours = totalSeconds / 3600
    val remainingSeconds = totalSeconds % 3600
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}