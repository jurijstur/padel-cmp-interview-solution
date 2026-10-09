package io.bifri.interview.cmp.padel.core.designsystem.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import io.bifri.interview.cmp.padel.core.designsystem.component.snackbar.LocalSnackbarHostState

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val effectiveModifier = if (scrollBehavior != null) {
        modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    } else {
        modifier
    }

    Scaffold(
        modifier = effectiveModifier,
        topBar = topBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = content,
    )
}
