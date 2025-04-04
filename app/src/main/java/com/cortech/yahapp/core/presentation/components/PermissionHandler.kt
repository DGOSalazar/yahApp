package com.cortech.yahapp.core.presentation.components

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StoragePermissionHandler(
    onPermissionGranted: () -> Unit
) {
    val readPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissionState = rememberPermissionState(
        readPermission
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        }
    }

    LaunchedEffect(permissionState) {
        if (!permissionState.status.isGranted && 
            !permissionState.status.shouldShowRationale) {
            permissionState.launchPermissionRequest()
        }
    }
}
