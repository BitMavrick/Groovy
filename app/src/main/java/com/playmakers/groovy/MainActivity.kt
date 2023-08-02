package com.playmakers.groovy

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.playmakers.groovy.permissions.PermissionHandler
import com.playmakers.groovy.ui.theme.GroovyTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

// Function to handle the logic when audio permission is granted
private fun onAudioPermissionGranted(context : Context) {
    showToast(context, "Permission Granted")
}

fun showToast(context : Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
