package dev.koga.hopi.feature.category_games

import dev.koga.hopi.feature.games.GamesUiState
import dev.koga.hopi.model.Category

data class CategoryGamesUiState(
    val category: Category,
    val gamesUiState: GamesUiState,
)