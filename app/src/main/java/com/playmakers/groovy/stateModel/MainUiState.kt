package com.playmakers.groovy.stateModel

import com.playmakers.groovy.model.Music

data class MainUiState(
    val loading: Boolean? = false,
    val musics: List<Music>? = emptyList(),
    val selectedMusic: Music? = null,
    val errorMessage: String? = null
)