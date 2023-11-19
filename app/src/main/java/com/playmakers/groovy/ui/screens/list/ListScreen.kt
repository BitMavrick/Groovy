package com.playmakers.groovy.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playmakers.groovy.R
import com.playmakers.groovy.ui.util.ListState

@Composable
fun ListScreen(){
    val listViewModel = hiltViewModel<ListViewModel>()
    val listUiState = listViewModel.listUiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(listUiState.listState == ListState.LOADING){
            Text(
                text = "Loading Music",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }else if(listUiState.listState == ListState.LOADED){
            Text(
                text = "Loaded Music",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }else if(listUiState.listState == ListState.NOT_FOUND){
            Text(
                text = "Music Not Found",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }else{
            Text(
                text = "Error!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

/*

val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.not_found))

    Column(
        modifier = Modifier
            .fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.height(400.dp),
        ){
            LottieAnimation(
                composition = composition,
                iterations = 100,
            )
        }

        Text(
            text = "Oops, looks like you don't have any music files!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }

 */