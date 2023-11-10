package com.playmakers.groovy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.service.MusicPlayBackService
import com.playmakers.groovy.stateModel.MainViewModel
import com.playmakers.groovy.ui.GroovyApp
import com.playmakers.groovy.ui.theme.GroovyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovyTheme {
                GroovyApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Debug", "DESTROYING THE MAIN ACTIVITY")
        mainViewModel.destroyMediaController()
        stopService(Intent(this, MusicPlayBackService::class.java))
    }
}