package com.playmakers.groovy.ui.screens.player

import com.playmakers.groovy.domain.model.RoomMusic

sealed class PlayerEvent {
     object PlayMusic : PlayerEvent()
     object ResumeMusic : PlayerEvent()
     object PauseMusic : PlayerEvent()
     object SkipNext : PlayerEvent()
     object SkipPrevious : PlayerEvent()
     object ShuffleAndPlay : PlayerEvent()
     object SetShuffleOn : PlayerEvent()
     object SetShuffleOff : PlayerEvent()
     object SetRepeatOneOn : PlayerEvent()
     object SetRepeatOneOff : PlayerEvent()
     object DestroyMediaSession : PlayerEvent()
     data class SeekMusicPosition(val position : Long) : PlayerEvent()
     data class OnMusicSelected(val selectedMusic: RoomMusic) : PlayerEvent()
     data class SetShuffleMode(val shuffleEnable : Boolean) : PlayerEvent()
}