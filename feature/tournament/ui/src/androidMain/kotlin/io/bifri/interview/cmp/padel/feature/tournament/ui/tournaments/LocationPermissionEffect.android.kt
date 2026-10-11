package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
internal actual fun LocationPermissionEffect(onResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnResult by rememberUpdatedState(onResult)
    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { grants ->
        currentOnResult(grants.values.any { granted -> granted })
    }
    DisposableEffect(lifecycleOwner, launcher) {
        fun syncPermission() {
            val granted = permissions.any { permission ->
                context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
            when {
                granted -> currentOnResult(true)

                !LocationPermissionPrompt.asked -> {
                    LocationPermissionPrompt.asked = true
                    launcher.launch(permissions)
                }

                else -> currentOnResult(false)
            }
        }

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) syncPermission()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            syncPermission()
        }
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}

private object LocationPermissionPrompt {
    var asked: Boolean = false
}
