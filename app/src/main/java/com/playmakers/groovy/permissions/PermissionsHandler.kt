package com.playmakers.groovy.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun PermissionHandler(
    activity: Activity
){
    val viewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val audioPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(
                permission = AUDIO_PERMISSION(),
                isGranted = isGranted
            )
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                audioPermissionResultLauncher.launch(
                    AUDIO_PERMISSION()
                )
            }
        ) {
            Text(text = "Request for audio access")
        }
    }

    dialogQueue.reversed().forEach { permission ->
        PermissionDialogScreen(
            permissionTextProvider = when (permission) {
                AUDIO_PERMISSION() -> {
                    AudioPermissionTextProvider()
                }
                else -> return@forEach },
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                AUDIO_PERMISSION()
            ),
            onDismiss = viewModel::dismissDialog,
            onOkClick =  {
                viewModel.dismissDialog()
                audioPermissionResultLauncher.launch(AUDIO_PERMISSION())
            },
            onGoToAppSettingsClick = { openAppSettings(activity) }
        )
    }
}

fun showToast(context : Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun AUDIO_PERMISSION(): String {
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