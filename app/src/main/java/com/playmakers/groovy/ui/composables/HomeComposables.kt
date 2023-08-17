package com.playmakers.groovy.ui.composables

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.R
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerStates
import com.playmakers.groovy.ui.screens.homeScreen.MusicViewModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxWidth(),
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Search your music") },
            leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        ) {
            repeat(4) { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                    modifier = Modifier
                        .clickable {
                            text = resultText
                            active = false
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}


// @Preview(showBackground = true)
@Composable
fun MusicList(music : Music, onItemClick: (Music) -> Unit){

    Row(
        Modifier
            .clickable {
                onItemClick(music)
            }
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ){
        Box(
            Modifier.clip(RoundedCornerShape(5.dp))
        ){
            Image(
                painter = painterResource(R.drawable.album_art),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f)
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = music.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = music.artist,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}

@Composable
fun MiniHeading(totalMusic : Int){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ){
        Text(
            text = "Name",
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "$totalMusic Songs",
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun BottomPlayback(
    musicViewModel: MusicViewModel
) {
    val playbackViewModel: PlaybackViewModel = viewModel()
    val isExpanded = playbackViewModel.isExpandedState.value

    if(musicViewModel.selectedMusic != null){
        Modifier.fillMaxWidth()

        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Row(
                if(isExpanded){
                    Modifier
                        .animateContentSize()
                        .fillMaxSize()

                }else{
                    Modifier
                        .clickable {
                            playbackViewModel.toggleExpandState()
                        }
                        .animateContentSize()
                        .fillMaxWidth()
                        .height(86.dp)
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                }
            ){
                if(isExpanded){
                    // The Playback screen will be here
                    PlaybackScreen(musicViewModel)
                }else{
                    Box(
                        Modifier.clip(RoundedCornerShape(5.dp))
                    ){
                        Image(
                            painter = painterResource(R.drawable.album_art),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.aspectRatio(1f)
                        )
                    }

                    Column(
                        Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = musicViewModel.selectedMusic?.title ?: " Loading ... ",
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = musicViewModel.selectedMusic?.artist ?: " Loading ... ",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(modifier = Modifier.align(Alignment.CenterVertically)
                    ){
                        if(musicViewModel.selectedMusic?.state == PlayerStates.STATE_BUFFERING){
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp)
                            )
                        }else{
                            IconButton(onClick = {
                                musicViewModel.onPlayPauseClick()
                            }) {
                                if(musicViewModel.selectedMusic?.state == PlayerStates.STATE_PLAYING){
                                    Icon(
                                        imageVector = Icons.Rounded.Pause,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }else{
                                    Icon(
                                        imageVector = Icons.Rounded.PlayArrow,
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
    }
}

@Composable
private fun PlaybackScreen(
    musicViewModel: MusicViewModel
){
    val playbackViewModel: PlaybackViewModel = viewModel()
    BackHandler {
        playbackViewModel.toggleExpandState()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        BrandBar()

        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.album_art),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = musicViewModel.selectedMusic?.title ?: "Unknown title",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1
        )

        Text(
            text = "${musicViewModel.selectedMusic?.artist ?: "Unknown artist"} • ${musicViewModel.selectedMusic?.album ?: "Unknown album"}",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLines = 1
        )

        MusicSlider(musicViewModel.playbackState){
            musicViewModel.onSeekBarPositionChanged(it)
        }

        PlaybackControl(musicViewModel)
    }
}

// @Preview(showBackground = true)
@Composable
private fun BrandBar(
    playbackViewModel: PlaybackViewModel = viewModel()
) {
    Row(
        Modifier.fillMaxWidth()
    ){
        IconButton(onClick = {
            playbackViewModel.toggleExpandState()
        }) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Groovy",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
            )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
            )
        }

    }
}

// @Preview(showBackground = true)
@SuppressLint("AutoboxingStateCreation")
@Composable
private fun MusicSlider(
    playbackState: StateFlow<PlaybackState>,
    onSeekBarPositionChanged: (Long) -> Unit
) {
    val playbackStateValue = playbackState.collectAsState(
        initial = PlaybackState(0L, 0L)
    ).value
    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableStateOf(0f) }

    Column {
        Slider(
            value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
            onValueChange = { currentPosTemp = it },
            onValueChangeFinished = {
                currentMediaProgress = currentPosTemp
                currentPosTemp = 0f
                onSeekBarPositionChanged(currentMediaProgress.toLong())
            },
            valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
            modifier = Modifier.semantics { this.contentDescription = "Localized Description" },
        )
        // Text(text = sliderPosition.toString())
    }
}

@Composable
private fun PlaybackControl(
    musicViewModel: MusicViewModel
){
    Row (
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        IconButton(onClick = {
            musicViewModel.onPreviousClick()
        }) {
            Icon(
                imageVector = Icons.Rounded.SkipPrevious,
                contentDescription = null,
                Modifier.size(70.dp)
            )
        }

        if(musicViewModel.selectedMusic?.state == PlayerStates.STATE_BUFFERING){
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp)
            )
        }else{
            IconButton(onClick = {
                musicViewModel.onPlayPauseClick()
            }) {
                if(musicViewModel.selectedMusic?.state == PlayerStates.STATE_PLAYING){
                    Icon(
                        imageVector = Icons.Rounded.Pause,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp)
                    )
                }
            }
        }

        IconButton(onClick = {
            musicViewModel.onNextClick()
        }) {
            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = null,
                Modifier.size(70.dp)
            )
        }
    }
}