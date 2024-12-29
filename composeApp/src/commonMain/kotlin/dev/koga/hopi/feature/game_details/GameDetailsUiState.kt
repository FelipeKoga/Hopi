package dev.koga.hopi.feature.game_details

import dev.koga.hopi.model.GameDetails

sealed interface GameDetailsUiState {
    data object Loading : GameDetailsUiState
    data class Success(val game: GameDetails) : GameDetailsUiState
    data object Error : GameDetailsUiState
}