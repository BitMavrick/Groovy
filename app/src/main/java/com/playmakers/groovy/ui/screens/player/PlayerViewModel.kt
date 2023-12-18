package com.playmakers.groovy.ui.screens.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.controller.AddMusic
import com.playmakers.groovy.controller.GetMusicPosition
import com.playmakers.groovy.controller.PauseMusic
import com.playmakers.groovy.controller.PlayMusic
import com.playmakers.groovy.controller.ResumeMusic
import com.playmakers.groovy.controller.SetMediaControlCallback
import com.playmakers.groovy.controller.SetShuffleMode
import com.playmakers.groovy.data.MusicsRepository
import com.playmakers.groovy.domain.model.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PlayerViewModel @Inject constructor (
    private val addMusic: AddMusic,
    private val playMusic: PlayMusic,
    private val resumeMusic: ResumeMusic,
    private val pauseMusic: PauseMusic,
    private val setMediaControlCallBack:SetMediaControlCallback,
    private val getCurrentMediaPosition: GetMusicPosition,
    private val setShuffleMode: SetShuffleMode,
    // private val destroyController: DestroyMusicPlaybackControl,
    private val roomMusicsRepository: MusicsRepository,
) : ViewModel() {
    var playerUiState by mutableStateOf(PlayerUiState())
        private set

    private fun addMusicToMedia(){
        viewModelScope.launch {
            val musicFlow = roomMusicsRepository.getAllMusicsStream()
            addMusic(musicFlow.first()) // --> Assumed: addMusic causes the music restart while reopen the app
            playerUiState.apply {
                musicList = musicFlow.first()
            }
        }
    }

    private fun setMediaControllerCallback(){
        setMediaControlCallBack { playerState, currentMusic, currentPosition, totalDuration, isShuffleEnabled, isRepeatOneEnabled ->
            playerUiState = playerUiState.copy(
                playerState = playerState,
                currentMusic = currentMusic,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                isShuffleEnabled = isShuffleEnabled,
                isRepeatOneEnabled = isRepeatOneEnabled
            )

            if(playerState == PlayerState.PLAYING){
                viewModelScope.launch {
                    while (true){
                        delay(1.seconds)
                        playerUiState = playerUiState.copy(
                            currentPosition = getCurrentMediaPosition()
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event : PlayerEvent){
        when(event){
            PlayerEvent.PlayMusic -> playMusic()

            PlayerEvent.ResumeMusic -> onResumeMusic()

            PlayerEvent.PauseMusic -> onPauseMusic()

            PlayerEvent.ShuffleAndPlay -> musicShuffleAndPlay()

            is PlayerEvent.OnMusicSelected -> {
                playerUiState = playerUiState.copy(selectedMusic = event.selectedMusic)
            }

            is PlayerEvent.SetShuffleMode -> {
                setMusicShuffleMode(event.shuffleEnable)
            }
        }
    }

    private fun playMusic(){
        playerUiState.apply {
            selectedMusic?.id?.let {
                playMusic(it.toInt())
            }
        }
    }

    private fun onResumeMusic() {
        resumeMusic()
    }

    private fun onPauseMusic() {
        pauseMusic()
    }

    private fun musicShuffleAndPlay(){
        playMusic()
        setShuffleMode(true)
    }

    private fun setMusicShuffleMode(isEnabled : Boolean){
        setShuffleMode(isEnabled)
    }

    /*
    fun destroyMediaController(){
        destroyController()
    }
    */

    init {
        addMusicToMedia()
        setMediaControllerCallback()
    }
}