package io.bifri.interview.cmp.padel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import io.bifri.interview.cmp.padel.core.designsystem.component.snackbar.LocalSnackbarHostState
import io.bifri.interview.cmp.padel.core.designsystem.theme.AppTheme
import io.bifri.interview.cmp.padel.core.ui.image.ProvideAppImageLoader
import io.bifri.interview.cmp.padel.navigation.AppNavDisplay
import io.bifri.interview.cmp.padel.navigation.rememberAppNavBackStack
import kotlinx.coroutines.launch

@Composable
fun App() {
    ProvideAppImageLoader()

    val backStack = rememberAppNavBackStack()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onError: (Throwable) -> Unit = { throwable ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = throwable.message.orEmpty(),
            )
        }
    }
    AppTheme {
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            AppNavDisplay(
                backStack = backStack,
                onError = onError,
            )
        }
    }
}
