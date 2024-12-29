package dev.koga.hopi.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.koga.hopi.model.SimpleGame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onGameClicked: (SimpleGame) -> Unit
) {
    val games by viewModel.games.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Hopi")
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(games) { game ->
                GameCard(
                    modifier = modifier,
                    game = game,
                    onClick = {
                        onGameClicked(game)
                    }
                )
            }
        }
    }
}

@Composable
fun GameCard(modifier: Modifier = Modifier, game: SimpleGame, onClick: () -> Unit) {
    Card(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = game.title,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = game.title)
                Text(text = game.description)
            }
        }
    }
}