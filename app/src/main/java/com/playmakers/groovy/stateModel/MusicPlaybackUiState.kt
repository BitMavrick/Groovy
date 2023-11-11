package com.playmakers.groovy.stateModel

import com.playmakers.groovy.model.Music
import com.playmakers.groovy.model.PlayerState

data class MusicPlaybackUiState (
    val playerState: PlayerState? = null,
    val currentMusic: Music? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)