package io.bifri.interview.cmp.padel.core.designsystem.component.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.bifri.interview.cmp.padel.core.designsystem.DesignSystemRes
import io.bifri.interview.cmp.padel.core.designsystem.cd_back_button
import io.bifri.interview.cmp.padel.core.designsystem.icon.AppIcon
import io.bifri.interview.cmp.padel.core.designsystem.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = LocalContentColor.current,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = AppIcon.ArrowBack,
            tint = iconTint,
            contentDescription = stringResource(DesignSystemRes.string.cd_back_button),
        )
    }
}

@Preview
@Composable
private fun BackIconButtonPreview() {
    AppTheme {
        BackIconButton(onClick = {})
    }
}
