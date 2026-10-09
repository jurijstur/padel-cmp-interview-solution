package io.bifri.interview.cmp.padel.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val margin: Dp,
    val spacingExtraSmall: Dp,
    val spacingSmall: Dp,
    val spacingMedium: Dp,
    val spacingLarge: Dp,
    val spacingExtraLarge: Dp,
    val cardPaddingSmall: Dp,
    val cardPaddingMedium: Dp,
    val cardPaddingLarge: Dp,
)

enum class MaterialDimens(val dimens: AppDimens) {
    Compact(
        AppDimens(
            margin = 16.dp,
            spacingExtraSmall = 4.dp,
            spacingSmall = 8.dp,
            spacingMedium = 16.dp,
            spacingLarge = 24.dp,
            spacingExtraLarge = 32.dp,
            cardPaddingSmall = 8.dp,
            cardPaddingMedium = 16.dp,
            cardPaddingLarge = 24.dp,
        ),
    ),
    Medium(
        AppDimens(
            margin = 24.dp,
            spacingExtraSmall = 6.dp,
            spacingSmall = 12.dp,
            spacingMedium = 24.dp,
            spacingLarge = 36.dp,
            spacingExtraLarge = 48.dp,
            cardPaddingSmall = 12.dp,
            cardPaddingMedium = 24.dp,
            cardPaddingLarge = 36.dp,
        ),
    ),
    Expanded(
        AppDimens(
            margin = 24.dp,
            spacingExtraSmall = 6.dp,
            spacingSmall = 12.dp,
            spacingMedium = 24.dp,
            spacingLarge = 36.dp,
            spacingExtraLarge = 48.dp,
            cardPaddingSmall = 12.dp,
            cardPaddingMedium = 24.dp,
            cardPaddingLarge = 36.dp,
        ),
    ),
}

internal val LocalAppDimens = staticCompositionLocalOf { MaterialDimens.Compact }

val dimens @Composable get() = LocalAppDimens.current.dimens
