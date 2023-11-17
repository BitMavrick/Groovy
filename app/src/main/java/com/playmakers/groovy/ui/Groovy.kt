package com.playmakers.groovy.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playmakers.groovy.R
import com.playmakers.groovy.ui.screens.list.ListViewModel
import com.playmakers.groovy.ui.screens.permission.PermissionScreen

@Composable
fun Groovy(
    activity: Activity
){
    PermissionScreen(activity = activity)
}




/*
    hiltViewModel<ListViewModel>()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.intro))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            iterations = 200,
        )
    }
 */