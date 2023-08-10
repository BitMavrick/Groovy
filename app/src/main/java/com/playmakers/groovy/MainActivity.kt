package com.playmakers.groovy

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.playmakers.groovy.ui.theme.GroovyTheme
import com.playmakers.groovy.ui.util.PermissionHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PermissionHandler(this, onAudioPermissionGranted ={
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