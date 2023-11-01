package com.playmakers.groovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.playmakers.groovy.ui.GroovyApp
import com.playmakers.groovy.ui.theme.GroovyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovyTheme {
                GroovyApp()
            }
        }
    }
}