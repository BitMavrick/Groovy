package com.playmakers.groovy.ui.screens.player

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

            }
        }
    }
}