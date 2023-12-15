package com.playmakers.groovy.ui.screens.player

import com.playmakers.groovy.domain.model.PlayerState
import com.playmakers.groovy.domain.model.RoomMusic
data class PlayerUiState (
    val playerState: PlayerState? = null,
    val currentMusic: RoomMusic? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false,
    val selectedMusic : RoomMusic? = null,
    var musicList: List<RoomMusic>? = emptyList(),
)