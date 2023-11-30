package com.playmakers.groovy.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playmakers.groovy.R
import com.playmakers.groovy.ui.components.MusicList
import com.playmakers.groovy.ui.util.ListState
import kotlinx.coroutines.delay

@Composable
fun ListScreen(){
    val listViewModel = hiltViewModel<ListViewModel>()
    val listUiState = listViewModel.listUiState.collectAsState().value
    val listEvent = listViewModel::onEvent

    when (listUiState.listState) {
        ListState.LOADING -> {
            Loading(
                loadingText = listUiState.loadingText
            )
        }
        ListState.LOADED -> {
            listUiState.musicList?.let {
                MusicList(
                    listMusic = it
                )
            }
        }
        ListState.NOT_FOUND -> {
            NotFound(
                onRefreshClick = {
                    listEvent(ListEvent.RefreshList)
                }
            )
        }
    }
}


@Composable
fun Loading(
    loadingText : String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LinearProgressIndicator(
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = loadingText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun NotFound(
    onRefreshClick: () -> Unit
){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.not_found))
    var isArtVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100L)
        isArtVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = isArtVisible) {
            Box(
                modifier = Modifier.height(400.dp),
            ){
                LottieAnimation(
                    composition = composition,
                    iterations = 30,
                )
            }
        }

        Text(
            text = "No music found!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Looks like you don't have any music files on this device.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(visible = isArtVisible){
            OutlinedButton(
                modifier = Modifier.padding(top = 30.dp),
                onClick = {
                    onRefreshClick()
                }
            ) {
                Text(
                    text = "Refresh",
                )
            }
        }
    }
}