package io.bifri.interview.cmp.padel.core.designsystem.component.placeholder

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

private const val ShimmerDurationMillis = 1200

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateX by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = ShimmerDurationMillis,
                easing = LinearEasing,
            ),
        ),
        label = "shimmerX",
    )
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth.toFloat()
        val brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surfaceVariant,
            ),
            start = Offset(translateX * width, 0f),
            end = Offset((translateX + 1f) * width, 0f),
        )
        Box(Modifier.matchParentSize().background(brush))
    }
}
