package com.playmakers.groovy.ui.screens.list

import com.playmakers.groovy.domain.model.Music
import com.playmakers.groovy.ui.util.ListState

data class ListUiState(
    val musicList : List<Music> = emptyList(),
    val listState : ListState = ListState.LOADING
)