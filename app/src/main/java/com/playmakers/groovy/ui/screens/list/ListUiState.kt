package com.playmakers.groovy.ui.screens.list

import com.playmakers.groovy.domain.model.RoomMusic
import com.playmakers.groovy.ui.util.ListState
import kotlinx.coroutines.flow.Flow

data class ListUiState(
    val musicList: Flow<List<RoomMusic>> ? = null,
    val musicListAsList: List<RoomMusic> = emptyList(),
    val loadingText : String = "Loading ...",
    val listState: ListState = ListState.LOADING,
    val refreshState : Boolean = false
)