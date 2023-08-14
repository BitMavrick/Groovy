package com.playmakers.groovy.ui.composables

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PlaybackViewModel : ViewModel(){
    val isExpandedState = mutableStateOf(true)
    fun toggleExpandState() {
        isExpandedState.value = !isExpandedState.value
    }
}