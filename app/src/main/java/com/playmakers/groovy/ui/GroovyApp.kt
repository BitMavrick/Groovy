package com.playmakers.groovy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.GroovyViewModelProvider
import com.playmakers.groovy.stateModel.MainViewModel

@Composable
fun GroovyApp() {
    val mainViewModel: MainViewModel = viewModel(
        factory = GroovyViewModelProvider.Factory
    )

    LazyColumn(
        Modifier.fillMaxSize()
    ){
        item{
            for(music in mainViewModel.getMusic()){
                Text(text = music.title)
            }
        }
    }
    

}