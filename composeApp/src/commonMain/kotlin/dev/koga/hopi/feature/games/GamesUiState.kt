package dev.koga.hopi.feature.games

import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions

sealed interface GamesUiState {
    data object Loading : GamesUiState
    data class Success(val games: List<SimpleGame>, val sortOptions: SortOptions) : GamesUiState
    data object Error : GamesUiState
}