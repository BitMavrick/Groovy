package com.playmakers.groovy.ui.screens.player

import androidx.lifecycle.ViewModel
import com.playmakers.groovy.controller.AddMusic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor (
    private val addMusic: AddMusic
) : ViewModel() {

}