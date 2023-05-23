package de.alexander13oster.easycomposebarcodescanner

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
@Composable
fun MainScreen() {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var analyzerType by remember { mutableStateOf(AnalyzerType.UNDEFINED) }

    if (cameraPermissionState.status.isGranted) {
        if (analyzerType == AnalyzerType.UNDEFINED) {
            Column {
                Button(onClick = { analyzerType = AnalyzerType.BARCODE }) {
                    Text(text = "BARCODE")
                }
                Button(onClick = { analyzerType = AnalyzerType.TEXT }) {
                    Text(text = "TEXT")
                }
            }
        } else {
            CameraScreen(analyzerType)
        }
    } else if (cameraPermissionState.status.shouldShowRationale) {
        Text("Camera Permission permanently denied")
    } else {
        SideEffect {
            cameraPermissionState.run { launchPermissionRequest() }
        }
        Text("No Camera Permission")
    }
}