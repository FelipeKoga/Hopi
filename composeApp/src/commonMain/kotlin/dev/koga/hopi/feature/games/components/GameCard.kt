package dev.koga.hopi.feature.games.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.koga.hopi.model.SimpleGame


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameCard(modifier: Modifier = Modifier, game: SimpleGame, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 160.dp),
                clipToBounds = false,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = game.description,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GameInfoBadge(text = game.genre)
                    GameInfoBadge(text = game.platform)
                    GameInfoBadge(text = game.releaseDate)
                }
            }
        }
    }
}

@Composable
private fun GameInfoBadge(modifier: Modifier = Modifier, text: String) {
    Badge(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary.copy(
            alpha = .1f
        ),
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 2.dp
            ),
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}