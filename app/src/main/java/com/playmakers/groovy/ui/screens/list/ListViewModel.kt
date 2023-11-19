package com.playmakers.groovy.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playmakers.groovy.domain.repository.MusicRepository
import com.playmakers.groovy.ui.util.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ListViewModel @Inject constructor (
    private val musicRepository: MusicRepository
): ViewModel() {
    private val _listUiState = MutableStateFlow(ListUiState())
    val listUiState: StateFlow<ListUiState> = _listUiState

    init {
        getMusicFiles()
    }

    private fun getMusicFiles(){
        _listUiState.update {
            it.copy(
                listState = ListState.LOADING
            )
        }

        viewModelScope.launch {
            delay(1.seconds)
            _listUiState.update {
                it.copy(
                    musicList = musicRepository.getMusicFiles(),
                )
            }

            if(_listUiState.value.musicList.isEmpty()){
                _listUiState.update {
                    it.copy(
                        listState = ListState.NOT_FOUND
                    )
                }
            }else{
                _listUiState.update {
                    it.copy(
                        listState = ListState.LOADED
                    )
                }
            }
        }
    }
}