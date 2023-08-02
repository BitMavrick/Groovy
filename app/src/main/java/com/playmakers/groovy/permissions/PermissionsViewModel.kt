package com.playmakers.groovy.permissions

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel(){
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val isPermissionGranted = mutableSetOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }else if (isGranted){
            isPermissionGranted.add(permission)
        }
    }
}