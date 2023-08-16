package com.playmakers.groovy.ui.screens.homeScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.data.getMusic
import com.playmakers.groovy.player.MyPlayer
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerEvents
import com.playmakers.groovy.player.PlayerStates
import com.playmakers.groovy.ui.util.collectPlayerState
import com.playmakers.groovy.ui.util.launchPlaybackStateJob
import com.playmakers.groovy.ui.util.resetTracks
import com.playmakers.groovy.ui.util.toMediaItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("EmptyMethod")
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val myPlayer: MyPlayer,
    application: Application
) : AndroidViewModel(application), PlayerEvents {

    @SuppressLint("StaticFieldLeak")
    val applicationContext: Context = application.applicationContext

    private val _music = mutableStateListOf<Music>()

    val musics: List<Music> get() = _music

    private var isMusicPlay: Boolean = false

    private var selectedMusic: Music? by mutableStateOf(null)
        private set

    private var selectedMusicIndex: Int by mutableStateOf(-1)

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))

    val playbackState: StateFlow<PlaybackState> get() = _playbackState

    private var isAuto: Boolean = false

    init {
        _music.addAll(getMusic(applicationContext))
        myPlayer.iniPlayer(musics.toMediaItemList())
        observePlayerState()
    }

    private fun onMusicSelected(index: Int){
        if(selectedMusicIndex == -1) isMusicPlay = true
        if(selectedMusicIndex == -1 || selectedMusicIndex != index){
            _music.resetTracks()
            selectedMusicIndex = index
            setUpTrack()
        }
    }

    private fun setUpTrack(){
        if(!isAuto) myPlayer.setUpTrack(selectedMusicIndex, isMusicPlay)
        isAuto = false
    }

    private fun updateState(state: PlayerStates){
        if (selectedMusicIndex != -1){
            isMusicPlay = state == PlayerStates.STATE_PLAYING
            _music[selectedMusicIndex].state = state
            _music[selectedMusicIndex].isSelected = true
            selectedMusic = null
            selectedMusic = musics[selectedMusicIndex]

            updatePlaybackState(state)

            if(state == PlayerStates.STATE_NEXT_TRACK){
                isAuto = true
                onNextClick()
            }

            if(state == PlayerStates.STATE_END) onMusicSelected(0)
        }
    }

    private fun observePlayerState(){
        viewModelScope.collectPlayerState(myPlayer, ::updateState)
    }

    private fun updatePlaybackState(state: PlayerStates){
        playbackStateJob?.cancel()
        playbackStateJob = viewModelScope.launchPlaybackStateJob(_playbackState, state, myPlayer)
    }


    override fun onPlayPauseClick() {
        myPlayer.playPause()
    }

    override fun onPreviousClick() {
        if(selectedMusicIndex > 0) onMusicSelected(selectedMusicIndex - 1)
    }

    override fun onNextClick() {
        if(selectedMusicIndex < musics.size - 1) onMusicSelected(selectedMusicIndex + 1)
    }

    override fun onTrackClick(music: Music) {
        onMusicSelected(musics.indexOf(music))
    }

    override fun onSeekBarPositionChanged(position: Long) {
        viewModelScope.launch { myPlayer.seekToPosition(position) }
    }

    override fun onCleared(){
        super.onCleared()
        myPlayer.releasePlayer()
    }

}