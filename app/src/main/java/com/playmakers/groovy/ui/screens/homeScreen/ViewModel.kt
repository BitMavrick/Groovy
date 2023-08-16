package com.playmakers.groovy.ui.screens.homeScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.playmakers.groovy.data.Music
import com.playmakers.groovy.player.MyPlayer
import com.playmakers.groovy.player.PlaybackState
import com.playmakers.groovy.player.PlayerEvents
import com.playmakers.groovy.repositories.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@Suppress("EmptyMethod")
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
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


    override fun onPlayPauseClick() {
        TODO("Not yet implemented")
    }

    override fun onPreviousClick() {
        TODO("Not yet implemented")
    }

    override fun onNextClick() {
        TODO("Not yet implemented")
    }

    override fun onTrackClick(music: Music) {
        TODO("Not yet implemented")
    }

    override fun onSeekBarPositionChanged(position: Long) {
        TODO("Not yet implemented")
    }


}