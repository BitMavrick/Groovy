package com.playmakers.groovy.permissions

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.playmakers.groovy.ui.home.HomeScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun PermissionHandler(
    activity: Activity,
    onAudioPermissionGranted: () -> Unit
) {
    val viewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

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
                onAudioPermissionGranted()
            }

            viewModel.onPermissionResult(
                permission = musicPermission(),
                isGranted = isGranted
            )
        }
    )

    if (isPermissionGranted){
        HomeScreen()
    }else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    audioPermissionResultLauncher.launch(
                        musicPermission()
                    )
                }
            ) {
                Text(text = "Please allow the audio permission")
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