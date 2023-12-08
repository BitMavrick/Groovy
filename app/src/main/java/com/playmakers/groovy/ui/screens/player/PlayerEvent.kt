package com.playmakers.groovy.ui.screens.player

sealed class PlayerEvent {
     object PlayMusic : PlayerEvent()
     object ResumeMusic : PlayerEvent()
     object PauseMusic : PlayerEvent()
     data class OnMusicSelected(val selectedMusic: String) : PlayerEvent()
}