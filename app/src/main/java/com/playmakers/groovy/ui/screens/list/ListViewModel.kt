package com.playmakers.groovy.ui.screens.list

import androidx.lifecycle.ViewModel
import com.playmakers.groovy.domain.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (
    private val repository: MusicRepository
): ViewModel() {



}