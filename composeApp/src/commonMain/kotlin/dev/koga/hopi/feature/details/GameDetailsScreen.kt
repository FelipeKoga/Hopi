package dev.koga.hopi.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    viewModel: GameDetailsViewModel,
    onBack: () -> Unit,
) {

    val gameState by viewModel.game.collectAsStateWithLifecycle()

    when (gameState) {
        GameDetailsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
            }
        }

        GameDetailsUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(modifier = Modifier.align(alignment = Alignment.Center), text = "Error!")
            }
        }

        is GameDetailsUiState.Success -> {
            val game = (gameState as GameDetailsUiState.Success).data
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(text = game.title)
                        }
                    )
                }
            ) { contentPadding ->
                Text(game.description)
            }
        }
    }
}