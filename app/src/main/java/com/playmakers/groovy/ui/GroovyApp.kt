package com.playmakers.groovy.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.GroovyViewModelProvider
import com.playmakers.groovy.stateModel.MainEvent
import com.playmakers.groovy.stateModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroovyApp() {
    val mainViewModel: MainViewModel = viewModel(
        factory = GroovyViewModelProvider.Factory
    )

    val event = mainViewModel::onEvent

    LazyColumn(
        Modifier.fillMaxSize()
    ){
        item{
            Text(
                text = "Groovy Music",
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        item{
            for(music in mainViewModel.getMusic()){
                Card(
                    onClick = {
                        event(MainEvent.OnMusicSelected(music))
                        event(MainEvent.PlayMusic)
                    }
                ) {
                    Text(
                        text = music.title,
                        modifier = Modifier.padding(vertical = 6.dp),
                    )
                }
            }
        }
    }
}