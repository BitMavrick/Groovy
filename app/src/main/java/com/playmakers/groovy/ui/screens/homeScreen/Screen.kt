package com.playmakers.groovy.ui.screens.homeScreen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.data.getMusic
import com.playmakers.groovy.ui.composables.BottomPlayback
import com.playmakers.groovy.ui.composables.MiniHeading
import com.playmakers.groovy.ui.composables.MusicList
import com.playmakers.groovy.ui.composables.TopSearchBar

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun HomeScreen() {

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

            val musicViewModel : MusicViewModel = viewModel()

            val context = LocalContext.current
            val musicList = getMusic(context)

            musicViewModel.updateMusicList(musicList)

            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                item {
                    MiniHeading()
                }
                items(musicViewModel.musicList) { music ->
                    MusicList(music, context)
                }
            }
        },
        bottomBar = {
            BottomPlayback()
        }
    )
}







