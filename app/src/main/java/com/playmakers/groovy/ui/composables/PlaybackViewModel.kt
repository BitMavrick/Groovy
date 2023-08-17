package com.playmakers.groovy.ui.composables

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PlaybackViewModel : ViewModel(){
    val isExpandedState = mutableStateOf(false)
    val isPlayingState = mutableStateOf(false)

    fun toggleExpandState() {
        isExpandedState.value = !isExpandedState.value
    }
    fun togglePlayState() {
        isPlayingState.value = !isPlayingState.value
    }
}