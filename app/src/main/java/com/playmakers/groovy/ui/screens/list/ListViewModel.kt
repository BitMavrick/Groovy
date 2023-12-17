package com.playmakers.groovy.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.controller.AddMusic
import com.playmakers.groovy.data.MusicsRepository
import com.playmakers.groovy.domain.repository.MusicRepository
import com.playmakers.groovy.ui.util.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (
    private val addMusic: AddMusic,
    private val musicRepository: MusicRepository,
    private val roomMusicsRepository: MusicsRepository,
): ViewModel() {
    private val _listUiState = MutableStateFlow(ListUiState())
    val listUiState: StateFlow<ListUiState> = _listUiState

    private fun getMusicFiles(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                _listUiState.update {
                    it.copy(
                        listState = ListState.LOADING
                    )
                }

                val quickFetchMusic = musicRepository.quickFetchMusicFiles()
                val dbTableSize = roomMusicsRepository.getTableSize()

                /* Note: Yes, it's possible!

                val musicFlow = musicRepository.getMusicsFlow()

                musicFlow.collect{music ->
                    Log.d("MusicFlow", "Music: ${music.title}")
                }
                */

                if (quickFetchMusic.isEmpty()){
                    roomMusicsRepository.clearMusic()
                    _listUiState.update {
                        it.copy(
                            listState = ListState.NOT_FOUND
                        )
                    }
                }else{
                    if(dbTableSize == 0){
                        _listUiState.update {
                            it.copy(
                                loadingText = "Getting music files ..."
                            )
                        }
                        roomMusicsRepository.insertAllMusic(musicRepository.getMusicFiles())
                    }else if(quickFetchMusic.size != dbTableSize){
                        _listUiState.update {
                            it.copy(
                                loadingText = "Updating the changes ..."
                            )
                        }
                        roomMusicsRepository.clearMusic()
                        roomMusicsRepository.insertAllMusic(musicRepository.getMusicFiles())
                    }

                    val musicList = roomMusicsRepository.getAllMusicsStreamAsList()

                    _listUiState.update {
                        it.copy(
                            musicListAsList = musicList,
                            listState = ListState.LOADED
                        )
                    }
                }
            }
        }
    }

    private fun refreshMusicList(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listUiState.update {
                    it.copy(
                        listState = ListState.LOADING,
                        refreshState = true,
                        loadingText = "Refreshing ..."
                    )
                }

                val quickFetchMusic = musicRepository.quickFetchMusicFiles()

                if(quickFetchMusic.isEmpty()){
                    roomMusicsRepository.clearMusic()
                    _listUiState.update {
                        it.copy(
                            refreshState = false,
                            listState = ListState.NOT_FOUND
                        )
                    }
                }else{
                    roomMusicsRepository.clearMusic()
                    roomMusicsRepository.insertAllMusic(musicRepository.getMusicFiles())

                    val musicList = roomMusicsRepository.getAllMusicsStreamAsList()

                    _listUiState.update {
                        it.copy(
                            musicListAsList = musicList,
                            refreshState = false,
                            listState = ListState.LOADED
                        )
                    }
                }
            }

            val musicFlow = roomMusicsRepository.getAllMusicsStream()
            addMusic(musicFlow.first()) // --> Assumed: addMusic causes the music restart while reopen the app
        }
    }

    fun onEvent(event: ListEvent){
        when(event){
            ListEvent.RefreshList -> refreshMusicList()
        }
    }

    init {
        getMusicFiles()
    }
}