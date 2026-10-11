package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
internal actual fun LocationPermissionEffect(onResult: (Boolean) -> Unit) {
    val currentOnResult by rememberUpdatedState(onResult)
    // CLLocationManager presents the system prompt. Denial is reported by LocationService.
    LaunchedEffect(Unit) {
        currentOnResult(true)
    }
}
