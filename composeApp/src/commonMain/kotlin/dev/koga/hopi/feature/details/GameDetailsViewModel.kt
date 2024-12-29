package dev.koga.hopi.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.repository.MmoRepository
import dev.koga.hopi.Route
import dev.koga.hopi.model.GameDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

sealed interface GameDetailsUiState {
    data object Loading : GameDetailsUiState
    data class Success(val data: GameDetails) : GameDetailsUiState
    data object Error : GameDetailsUiState
}


class GameDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MmoRepository,
) : ViewModel() {

    private val id = savedStateHandle.toRoute<Route.GameDetails>().id

    @OptIn(ExperimentalCoroutinesApi::class)
    val gameState = repository.getById(id = id).mapLatest {
        GameDetailsUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GameDetailsUiState.Loading
    )

}