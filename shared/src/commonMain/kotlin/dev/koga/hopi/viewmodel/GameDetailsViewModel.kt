@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.koga.hopi.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koga.hopi.repository.GameRepository
import dev.koga.hopi.model.GameDetails
import dev.koga.hopi.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

sealed interface GameDetailsUiState {
    data object Loading : GameDetailsUiState
    data class Success(val game: GameDetails) : GameDetailsUiState
    data object Error : GameDetailsUiState
}

class GameDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    repository: GameRepository,
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")!!

    val gameState = repository.getById(id = id).mapLatest {
        when (it) {
            is Resource.Success -> GameDetailsUiState.Success(it.data)
            Resource.Error -> GameDetailsUiState.Error
            Resource.Loading -> GameDetailsUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileViewSubscribed,
        initialValue = GameDetailsUiState.Loading
    )

}