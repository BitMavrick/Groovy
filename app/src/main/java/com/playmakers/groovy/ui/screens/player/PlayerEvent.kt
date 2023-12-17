package com.playmakers.groovy.ui.screens.player

import com.playmakers.groovy.domain.model.RoomMusic

sealed class PlayerEvent {
     object PlayMusic : PlayerEvent()
     object ResumeMusic : PlayerEvent()
     object PauseMusic : PlayerEvent()
     object ShuffleAndPlay : PlayerEvent()
     object PlayerExpand : PlayerEvent()
     object PlayerCollapse : PlayerEvent()
     data class OnMusicSelected(val selectedMusic: RoomMusic) : PlayerEvent()
     data class SetShuffleMode(val shuffleEnable : Boolean) : PlayerEvent()
}