package com.playmakers.groovy.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import com.playmakers.groovy.ui.screens.permission.PermissionScreen

@Composable
fun Groovy(
    activity: Activity
){
    PermissionScreen(activity = activity)
}