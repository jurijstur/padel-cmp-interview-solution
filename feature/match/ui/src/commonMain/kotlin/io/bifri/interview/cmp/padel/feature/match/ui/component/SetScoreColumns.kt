package io.bifri.interview.cmp.padel.feature.match.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens

// A padel match is best of three. Empty columns keep set 1 in the same place
// when one row has two sets and another has three.
private const val SetColumnCount = 3

// "6(88)" is a two-digit tie-break, the widest score these columns need to fit.
// The extra dp keeps the last glyph from clipping.
private const val WidestSetScoreSample = "6(88)"
private val SetScoreColumnSlack = 1.dp

@Composable
internal fun SetScoreColumns(
    scores: List<String?>,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    val columnWidth = rememberSetScoreColumnWidth()
    val columns = List(maxOf(scores.size, SetColumnCount)) { index -> scores.getOrNull(index) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        columns.forEach { score ->
            Text(
                text = score.orEmpty(),
                modifier = Modifier.width(columnWidth),
                color = color,
                fontWeight = fontWeight,
                textAlign = TextAlign.End,
                style = style,
                maxLines = 1,
                softWrap = false,
            )
        }
    }
}

@Composable
private fun rememberSetScoreColumnWidth(): Dp {
    val style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val width = remember(textMeasurer, style, density) {
        val measured = textMeasurer.measure(
            text = WidestSetScoreSample,
            style = style,
            maxLines = 1,
            softWrap = false,
        )
        with(density) { measured.size.width.toDp() }
    }
    return width + SetScoreColumnSlack
}
