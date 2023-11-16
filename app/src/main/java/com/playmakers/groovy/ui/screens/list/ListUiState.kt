package com.playmakers.groovy.ui.screens.list

import com.playmakers.groovy.domain.model.Music

data class ListUiState(
    val musicList : List<Music> = emptyList()
)