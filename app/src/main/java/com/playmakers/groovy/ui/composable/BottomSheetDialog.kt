package com.playmakers.groovy.ui.composable

import com.playmakers.groovy.models.Track
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerEvents
import kotlinx.coroutines.flow.StateFlow

class BottomSheetDialog(
    selectedTrack: Track,
    playerEvents: PlayerEvents,
    playbackState: StateFlow<PlaybackState>
) {
}