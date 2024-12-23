package dev.koga.mmofinder.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.koga.mmofinder.Game

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    val games by viewModel.games.collectAsStateWithLifecycle()

    LazyColumn {
        items(games) { game ->
            GameCard(
                modifier = modifier,
                game = game
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameCard(modifier: Modifier = Modifier, game: Game) {
    Card(modifier = modifier, onClick = {}) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = game.title)
        }
    }
}