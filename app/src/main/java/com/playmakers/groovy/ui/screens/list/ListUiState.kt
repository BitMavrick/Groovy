package com.playmakers.groovy.ui.screens.list

import com.playmakers.groovy.data.room.RoomMusic
import com.playmakers.groovy.ui.util.ListState

data class ListUiState(
    val musicList : List<RoomMusic> = emptyList(),
    val listState : ListState = ListState.LOADING
)