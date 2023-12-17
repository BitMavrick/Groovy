package com.playmakers.groovy.ui.screens.player

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
){
    val playerUiState = playerViewModel.playerUiState
    val playerEvent = playerViewModel::onEvent

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        val musicSource by remember { mutableStateOf(playerUiState.currentMusic?.source) }
        var musicFile by remember { mutableStateOf(playerUiState.currentMusic) }

        LaunchedEffect(Unit){
            withContext(Dispatchers.IO) {
                musicFile = musicSource?.let { playerViewModel.getMusicBySource(it) }
            }
        }

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
            }
        ) {
            if(playerUiState.isPlayerExpanded) {
                BackHandler {
                    playerEvent(PlayerEvent.PlayerCollapse)
                }
            }else{
                if(musicFile != null){
                    Box(
                        Modifier.clip(RoundedCornerShape(5.dp))
                    ){
                        if (musicFile != null) {
                            musicFile!!.actualImage?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "Album art",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.aspectRatio(1f)
                                )
                            }
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
                        playerUiState.currentMusic?.source?.let {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1
                            )
                        }
                    }
                }else {
                    Text(text = "Null music")
                }
            }
        }
    }
}