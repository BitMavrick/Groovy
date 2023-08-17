package com.playmakers.groovy.ui.screens.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.playmakers.groovy.ui.composables.BottomPlayback
import com.playmakers.groovy.ui.composables.MiniHeading
import com.playmakers.groovy.ui.composables.MusicList
import com.playmakers.groovy.ui.composables.TopSearchBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(musicViewModel: MusicViewModel) {

    Scaffold(
        topBar = { TopSearchBar() },

        /* TODO: The floating action button (shuffle feature) will be implemented later version
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
        */

        content = { innerPadding ->
            val musicFiles = musicViewModel.musics

            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                item {
                    MiniHeading(musicFiles.size)
                }
                
                items(count = musicFiles.size) { it ->
                    MusicList(
                        musicFiles[it],
                        onItemClick = { musicViewModel.onTrackClick(it) }
                    )
                }
            }
        },

        bottomBar = {
            AnimatedVisibility(
                visible = musicViewModel.selectedMusic != null,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight })
            ) {
                BottomPlayback(musicViewModel)
            }
        }
    )
}







