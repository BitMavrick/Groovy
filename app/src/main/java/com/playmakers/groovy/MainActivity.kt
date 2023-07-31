package com.playmakers.groovy

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.playmakers.groovy.permissions.AUDIO_PERMISSION
import com.playmakers.groovy.permissions.PermissionHandler
import com.playmakers.groovy.ui.home.HomeScreen
import com.playmakers.groovy.ui.theme.GroovyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
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
                    if(isPermissionGranted(this, AUDIO_PERMISSION())){
                        HomeScreen()
                    }else{
                        PermissionHandler(this)
                    }
                }
            }
        }
    }
}

fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}