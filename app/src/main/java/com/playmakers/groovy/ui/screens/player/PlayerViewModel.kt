package com.playmakers.groovy.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.controller.AddMusic
import com.playmakers.groovy.controller.DestroyMusicPlaybackControl
import com.playmakers.groovy.controller.GetMusicPosition
import com.playmakers.groovy.controller.PauseMusic
import com.playmakers.groovy.controller.PlayMusic
import com.playmakers.groovy.controller.ResumeMusic
import com.playmakers.groovy.controller.SetMediaControlCallback
import com.playmakers.groovy.data.MusicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor (
    private val addMusic: AddMusic,
    private val playMusic: PlayMusic,
    private val resumeMusic: ResumeMusic,
    private val pauseMusic: PauseMusic,
    private val setMediaControlCallBack:SetMediaControlCallback,
    private val getCurrentMediaPosition: GetMusicPosition,
    private val destroyController: DestroyMusicPlaybackControl,
    private val roomMusicsRepository: MusicsRepository,
) : ViewModel() {
    private fun addMusicToMedia(){
        viewModelScope.launch {
            val musicFlow = roomMusicsRepository.getAllMusicsStream()
            addMusic(musicFlow.first())
        }
    }

    init {
        addMusicToMedia()
    }
}