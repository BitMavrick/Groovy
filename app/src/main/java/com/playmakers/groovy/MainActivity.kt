package com.playmakers.groovy

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.playmakers.groovy.ui.screens.homeScreen.MusicViewModel
import com.playmakers.groovy.ui.theme.GroovyTheme
import com.playmakers.groovy.ui.util.PermissionHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val musicViewModel : MusicViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PermissionHandler( musicViewModel,this, onAudioPermissionGranted ={
                        onAudioPermissionGranted(this)
                    })
                }
            }
        }
    }
}

private fun onAudioPermissionGranted(context : Context) {
    showToast(context, "Permission Granted")
}

fun showToast(context : Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}