package com.playmakers.groovy.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.models.Track
import com.playmakers.groovy.player.MyPlayer
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerEvents
import com.playmakers.groovy.player.PlayerStates
import com.playmakers.groovy.player.PlayerStates.STATE_END
import com.playmakers.groovy.player.PlayerStates.STATE_NEXT_TRACK
import com.playmakers.groovy.player.PlayerStates.STATE_PLAYING
import com.playmakers.groovy.repositories.TrackRepository
import com.playmakers.groovy.util.collectPlayerState
import com.playmakers.groovy.util.launchPlaybackStateJob
import com.playmakers.groovy.util.resetTracks
import com.playmakers.groovy.util.toMediaItemList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    trackRepository: TrackRepository,
    private val myPlayer: MyPlayer
) : ViewModel(), PlayerEvents {
    private val _tracks = mutableStateListOf<Track>()

    val tracks: List<Track> get() = _tracks

    private var isTrackPlay: Boolean = false

    var selectedTrack: Track? by mutableStateOf(null)
        private set

    private var selectedTrackIndex: Int by mutableStateOf(-1)

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))

    val playbackState: StateFlow<PlaybackState> get() = _playbackState

    private var isAuto: Boolean = false

    init {
        _tracks.addAll(trackRepository.getTrackList())
        myPlayer.iniPlayer(tracks.toMediaItemList())
        // observePlayerState()
    }

    private fun onTrackSelected(index: Int) {
        if (selectedTrackIndex == -1) isTrackPlay = true
        if (selectedTrackIndex == -1 || selectedTrackIndex != index) {
            _tracks.resetTracks()
            selectedTrackIndex = index
            setUpTrack()
        }
    }

    private fun setUpTrack() {
        if (!isAuto) myPlayer.setUpTrack(selectedTrackIndex, isTrackPlay)
        isAuto = false
    }

    private fun updateState(state: PlayerStates) {
        if (selectedTrackIndex != -1) {
            isTrackPlay = state == STATE_PLAYING
            _tracks[selectedTrackIndex].state = state
            _tracks[selectedTrackIndex].isSelected = true
            selectedTrack = null
            selectedTrack = tracks[selectedTrackIndex]

            updatePlaybackState(state)
            if (state == STATE_NEXT_TRACK) {
                isAuto = true
                onNextClick()
            }
            if (state == STATE_END) onTrackSelected(0)
        }
    }

    private fun observePlayerState() {
        viewModelScope.collectPlayerState(myPlayer, ::updateState)
    }

    private fun updatePlaybackState(state: PlayerStates) {
        playbackStateJob?.cancel()
        playbackStateJob = viewModelScope.launchPlaybackStateJob(_playbackState, state, myPlayer)
    }

    override fun onPreviousClick() {
        if (selectedTrackIndex > 0) onTrackSelected(selectedTrackIndex - 1)
    }

    override fun onNextClick() {
        if (selectedTrackIndex < tracks.size - 1) onTrackSelected(selectedTrackIndex + 1)
    }

    override fun onPlayPauseClick() {
        myPlayer.playPause()
    }

    override fun onTrackClick(track: Track) {
        onTrackSelected(tracks.indexOf(track))
    }

    override fun onSeekBarPositionChanged(position: Long) {
        viewModelScope.launch { myPlayer.seekToPosition(position) }
    }

    override fun onCleared() {
        super.onCleared()
        myPlayer.releasePlayer()
    }
}
