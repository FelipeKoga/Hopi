@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.koga.hopi.feature.game_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.koga.hopi.repository.GameRepository
import dev.koga.hopi.Route
import dev.koga.hopi.model.GameDetails
import dev.koga.hopi.model.Resource
import dev.koga.hopi.util.ext.WhileViewSubscribed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class GameDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    repository: GameRepository,
) : ViewModel() {

    private val id = savedStateHandle.toRoute<Route.GameDetails>().id

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