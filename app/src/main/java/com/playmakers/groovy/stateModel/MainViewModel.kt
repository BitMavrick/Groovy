package com.playmakers.groovy.stateModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.control.AddMusic
import com.playmakers.groovy.control.DestroyMusicPlayBackControl
import com.playmakers.groovy.control.PauseMusic
import com.playmakers.groovy.control.PlayMusic
import com.playmakers.groovy.control.ResumeMusic
import com.playmakers.groovy.model.Music
import kotlinx.coroutines.launch

class MainViewModel(
    private val musicList: List<Music>,
    private val addMusic: AddMusic,
    private val playMusic: PlayMusic,
    private val resumeMusic: ResumeMusic,
    private val pauseMusic: PauseMusic,
    private val destroyController: DestroyMusicPlayBackControl
) : ViewModel() {
    private var mainUiState by mutableStateOf(MainUiState())

    fun getMusic() : List<Music> {
        addMusic(musicList)

        mainUiState = mainUiState.copy(
            loading = false,
            musics = musicList
        )

        return musicList
    }

//    init {
//        addMusic(musicList)
//    }
//
//    fun updateMusic(){
//        mainUiState = mainUiState.copy(
//            loading = false,
//            musics = musicList
//        )
//    }


    fun onEvent(event: MainEvent){
        when (event) {
            MainEvent.PlayMusic -> playMusic()

            MainEvent.ResumeMusic -> resumeMusic()

            MainEvent.PauseMusic -> pauseMusic()

            is MainEvent.OnMusicSelected -> {
                mainUiState = mainUiState.copy(selectedMusic = event.selectedMusic)
            }
        }
    }

    private fun playMusic() {
        mainUiState.apply {
            musics?.indexOf(selectedMusic)?.let {
                playMusic(it)
                Log.d("Debug", "IN THE PLAY MUSIC FUNCTION!! $it")
            }
        }
    }

    fun play(int: Int){
        addMusic(musicList)
        playMusic(int)
    }

    private fun resumeMusic() {
        resumeMusic()
    }

    private fun pauseMusic() {
        pauseMusic()
    }

    fun destroyMediaController(){
        destroyController()
    }
}