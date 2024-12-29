package dev.koga.hopi.feature.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.koga.hopi.model.GameDetails
import dev.koga.hopi.model.MinimumSystemRequirements

data class GameInfo(
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit,
)

@Composable
fun GameDetails.toList(): List<GameInfo> {
    return buildList {
        add(GameInfo(title = "Genre", description = genre, icon = {}))
        add(GameInfo(title = "Platform", description = platform, icon = {}))
        add(GameInfo(title = "Release Date", description = releaseDate, icon = {}))
        add(GameInfo(title = "Developer", description = developer, icon = {}))
    }
}

fun MinimumSystemRequirements.toList(): List<GameInfo> {
    return buildList {
        add(
            GameInfo(
                title = "Operating System",
                description = os,
                icon = {}
            )
        )
        add(
            GameInfo(
                title = "Processor",
                description = processor,
                icon = {}
            )
        )
        add(
            GameInfo(
                title = "Memory",
                description = memory,
                icon = {}
            )
        )
        add(
            GameInfo(
                title = "Graphics",
                description = graphics,
                icon = {}
            )
        )
        add(
            GameInfo(
                title = "Storage",
                description = storage,
                icon = {}
            )
        )
    }
}


@Composable
fun GameInfoCard(
    modifier: Modifier = Modifier,
    info: GameInfo
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row {
                info.icon()

                Text(
                    text = info.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                    )
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = info.description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}