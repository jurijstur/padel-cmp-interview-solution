package io.bifri.interview.cmp.padel.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> LifecycleCollectEffect(
    stream: Flow<T>,
    vararg keys: Any?,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = STARTED,
    collector: (T) -> Unit,
) {
    val currentCollector by rememberUpdatedState(collector)

    LaunchedEffect(stream, lifecycle, minActiveState, *keys) {
        withContext(Dispatchers.Main.immediate) {
            lifecycle.repeatOnLifecycle(minActiveState) {
                stream.collect(currentCollector)
            }
        }
    }
}
