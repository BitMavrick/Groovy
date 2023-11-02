package com.playmakers.groovy.stateModel

import com.playmakers.groovy.model.Music

sealed class MainEvent {
    object PlayMusic : MainEvent()
    object ResumeMusic : MainEvent()
    object PauseMusic : MainEvent()
    data class OnMusicSelected(val selectedMusic: Music) : MainEvent()
}