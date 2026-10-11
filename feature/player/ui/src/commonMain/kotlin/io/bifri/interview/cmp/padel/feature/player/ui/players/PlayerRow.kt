package io.bifri.interview.cmp.padel.feature.player.ui.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import io.bifri.interview.cmp.padel.core.designsystem.component.placeholder.ShimmerBox
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens
import io.bifri.interview.cmp.padel.feature.player.domain.model.Player
import io.bifri.interview.cmp.padel.feature.player.ui.PlayerUiRes
import io.bifri.interview.cmp.padel.feature.player.ui.players_avatar_cd
import io.bifri.interview.cmp.padel.feature.player.ui.players_rank
import io.bifri.interview.cmp.padel.feature.player.ui.players_unranked
import org.jetbrains.compose.resources.stringResource

private val PlayerAvatarSize = 40.dp

@Composable
internal fun PlayerRow(
    player: Player,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.cardPaddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
        ) {
            PlayerAvatar(
                photoUrl = player.photoUrl,
                contentDescription = stringResource(PlayerUiRes.string.players_avatar_cd, player.name),
            )
            Column(verticalArrangement = Arrangement.spacedBy(dimens.spacingExtraSmall)) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = player.formatDetails(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun Player.formatDetails(): String {
    val rank = ranking?.takeIf { it.isNotBlank() }?.let {
        stringResource(PlayerUiRes.string.players_rank, it)
    } ?: stringResource(PlayerUiRes.string.players_unranked)
    return listOfNotNull(rank, nationality?.takeIf { it.isNotBlank() }).joinToString(" · ")
}

@Composable
private fun PlayerAvatar(photoUrl: String?, contentDescription: String) {
    SubcomposeAsyncImage(
        model = photoUrl,
        contentDescription = contentDescription,
        modifier = Modifier.size(PlayerAvatarSize).clip(CircleShape),
        contentScale = ContentScale.Crop,
        loading = { ShimmerBox(Modifier.matchParentSize()) },
        error = {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
        },
    )
}
