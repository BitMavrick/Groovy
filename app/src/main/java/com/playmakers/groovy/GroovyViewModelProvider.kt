package com.playmakers.groovy

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.playmakers.groovy.stateModel.MainViewModel

object GroovyViewModelProvider{
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(
                GroovyApplication().musicList,
                GroovyApplication().playMusic
            )
        }
    }
}

fun CreationExtras.GroovyApplication() : GroovyApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GroovyApplication)