package io.bifri.interview.cmp.padel.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAppDimens provides MaterialDimens.Compact) {
        MaterialExpressiveTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = appTypography(),
            content = content,
        )
    }
}
