@file:Suppress("DEPRECATION")

package com.playmakers.groovy.player

import androidx.media3.common.MediaItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.playmakers.groovy.player.PlayerStates.STATE_IDLE
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MyPlayer @Inject constructor(private val player: ExoPlayer) : Player.Listener {

    val playerState = MutableStateFlow(STATE_IDLE)

    val currentPlaybackPosition: Long
        get() = if (player.currentPosition > 0) player.currentPosition else 0L

    val currentTrackDuration: Long
        get() = if (player.duration > 0) player.duration else 0L

    fun iniPlayer(trackList: MutableList<MediaItem>) {
        player.addListener(this)
        player.setMediaItems(trackList)
        player.prepare()
    }
}