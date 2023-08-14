package com.playmakers.groovy.ui.composables

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel(){
    val isExpandedState = mutableStateOf(false)

    fun toggleExpandState() {
        isExpandedState.value = !isExpandedState.value
    }
}