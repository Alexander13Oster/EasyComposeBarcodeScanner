package de.alexander13oster.easycomposebarcodescanner

import android.Manifest
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
@Composable
fun MainScreen() {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        CameraScreen()
    } else if (cameraPermissionState.status.shouldShowRationale) {
        Text("Camera Permission permanently denied")
    } else {
        SideEffect {
            cameraPermissionState.run { launchPermissionRequest() }
        }
        Text("No Camera Permission")
    }
}