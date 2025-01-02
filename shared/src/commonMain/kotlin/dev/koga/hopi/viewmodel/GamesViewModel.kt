@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package dev.koga.hopi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions
import dev.koga.hopi.repository.GameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class GamesUiState(
    val sortOptions: SortOptions = SortOptions.empty,
    val data: Resource<List<SimpleGame>>
)

class GamesViewModel(
    private val repository: GameRepository,
) : ViewModel() {

    private val sortOptions = MutableStateFlow(SortOptions(platform = null, order = null))
    private val gamesResource = sortOptions.flatMapLatest { repository.getAll(sortOptions = it) }

    val uiState = combine(
        sortOptions,
        gamesResource,
    ) { sortOptions, gamesResource ->
        GamesUiState(
            sortOptions = sortOptions,
            data = gamesResource
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileViewSubscribed,
        initialValue = GamesUiState(sortOptions = sortOptions.value, data = Resource.Loading)
    )

    fun onSubmitSortOptions(options: SortOptions) {
        sortOptions.update { options }
    }
}