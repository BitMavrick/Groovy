package com.playmakers.groovy.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.GroovyViewModelProvider
import com.playmakers.groovy.stateModel.MainViewModel

@Composable
fun GroovyApp() {
    val mainViewModel: MainViewModel = viewModel(
        factory = GroovyViewModelProvider.Factory
    )
    
    Text(text = "Hello this is Groovy!")
}