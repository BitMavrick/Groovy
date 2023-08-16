package com.playmakers.groovy.ui.screens.homeScreen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.playmakers.groovy.ui.composables.BottomPlayback
import com.playmakers.groovy.ui.composables.MiniHeading
import com.playmakers.groovy.ui.composables.MusicList
import com.playmakers.groovy.ui.composables.TopSearchBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(musicViewModel: MusicViewModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = { TopSearchBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* fab click handler */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Shuffle")
            }
        },
        content = { innerPadding ->

            // val musicFiles = rememberSaveable { getMusic(context) }
            val musicFiles = musicViewModel.musics

            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                item {
                    MiniHeading()
                }
                
                items(count = musicFiles.size) {
                    MusicList(musicFiles[it]) { clickedMusic ->
                        // musicPlayer.playMusic(clickedMusic.contentUri!!)
                    }
                }
            }
        },

        bottomBar = {
            BottomPlayback()
        }
    )
}







