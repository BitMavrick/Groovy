package com.playmakers.groovy.ui.screens.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playmakers.groovy.R
import com.playmakers.groovy.ui.util.AudioPermissionTextProvider
import com.playmakers.groovy.ui.util.PermissionDialogScreen

@Composable
fun PermissionScreen(
    activity: Activity
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.intro))
    val viewModel = viewModel<PermissionViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Triggering the animation by changing the visibility state
        isVisible = true
    }

    var isPermissionGranted by remember {
        mutableStateOf(
            isPermissionGranted(activity, musicPermission())
        )
    }

    val audioPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            if(isGranted){
                isPermissionGranted = true
                // onAudioPermissionGranted()
            }

            viewModel.onPermissionResult(
                permission = musicPermission(),
                isGranted = isGranted
            )
        }
    )

    if (isPermissionGranted){
        // HomeScreen(musicViewModel)
    }else{

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = TweenSpec(1000)),
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Groovy Music",
                    style = MaterialTheme.typography.displaySmall
                )

                LottieAnimation(
                    composition = composition,
                    iterations = 100,
                )

                Text(
                    text = "Groovy needs audio access\npermission to play your music.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(42.dp))

                Button(
                    onClick = {
                        audioPermissionResultLauncher.launch(
                            musicPermission()
                        )
                    }
                ) {
                    Text(
                        text = "Grant Permission",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }

    dialogQueue.reversed().forEach { permission ->
        PermissionDialogScreen(
            permissionTextProvider = when (permission) {
                musicPermission() -> {
                    AudioPermissionTextProvider()
                }
                else -> return@forEach
            },
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                musicPermission()
            ),
            onDismiss = viewModel::dismissDialog,
            onOkClick = {
                viewModel.dismissDialog()
                audioPermissionResultLauncher.launch(musicPermission())
            },
            onGoToAppSettingsClick = {
                openAppSettings(activity)
            }
        )
    }
}

fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun musicPermission(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
}

fun openAppSettings(activity: Activity) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity.packageName, null)
    )
    activity.startActivity(intent)
}

@Composable
@Preview(showBackground = true)
private fun PermissionHandlerPreview() {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.intro))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Groovy Music",
            style = MaterialTheme.typography.displaySmall
        )

        LottieAnimation(
            composition = composition,
            iterations = 10,
        )

        Text(
            text = "Groovy needs audio access\npermission to play your music.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(42.dp))

        Button(
            onClick = {}
        ) {
            Text(
                text = "Grant Permission",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

