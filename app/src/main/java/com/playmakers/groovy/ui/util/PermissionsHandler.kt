package com.playmakers.groovy.ui.util

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.playmakers.groovy.ui.screens.homeScreen.HomeScreen
import com.playmakers.groovy.ui.screens.homeScreen.MusicViewModel
import com.playmakers.groovy.ui.screens.permissionsScreen.AudioPermissionTextProvider
import com.playmakers.groovy.ui.screens.permissionsScreen.PermissionDialogScreen
import com.playmakers.groovy.ui.screens.permissionsScreen.PermissionsViewModel

@Composable
fun PermissionHandler(
    musicViewModel: MusicViewModel,
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
        HomeScreen(musicViewModel)
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Groovy, \nA Simple, Minimalistic \n& Open-source Music Player",
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.height(72.dp))

            OutlinedButton(
                onClick = {
                    audioPermissionResultLauncher.launch(
                        musicPermission()
                    )
                }
            ) {
                Text(
                    text = "Allow audio permission",
                    style = MaterialTheme.typography.titleMedium
                )
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Groovy, \nA Simple, Minimalistic \n& Open-source Music Player",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(72.dp))

        OutlinedButton(
            onClick = {}
        ) {
            Text(
                text = "Allow audio permission",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}