package com.playmakers.groovy.ui.screens.permissionsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel(){
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}