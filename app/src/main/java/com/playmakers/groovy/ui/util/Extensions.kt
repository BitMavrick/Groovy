package com.playmakers.groovy.ui.util

import androidx.media3.common.MediaItem
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.player.MyPlayer
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerStates
import com.playmakers.groovy.player.PlayerStates.STATE_IDLE
import com.playmakers.groovy.player.PlayerStates.STATE_PLAYING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun MutableList<Music>.resetTracks() {
    this.forEach{music ->
        music.isSelected = false
        music.state = STATE_IDLE
    }
}

fun List<Music>.toMediaItemList() : MutableList<MediaItem> {
    return this.map { MediaItem.fromUri(it.path) }.toMutableList()
}

fun CoroutineScope.collectPlayerState(
    myPlayer: MyPlayer, updateState: (PlayerStates) -> Unit
){
    this.launch {
        myPlayer.playerState.collect{
            updateState(it)
        }
    }
}

fun CoroutineScope.launchPlaybackStateJob(
    playbackStateFlow: MutableStateFlow<PlaybackState>, state: PlayerStates, myPlayer: MyPlayer
) = launch {
    do {
        playbackStateFlow.emit(
            PlaybackState(
                currentPlaybackPosition = myPlayer.currentPlaybackPosition,
                currentTrackDuration = myPlayer.currentTrackDuration
            )
        )
        delay(1000)
    } while (state == STATE_PLAYING && isActive)
}

fun Long.formatTime() : String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}