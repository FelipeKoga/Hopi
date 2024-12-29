package dev.koga.hopi.feature.category_games

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.koga.hopi.Route
import dev.koga.hopi.model.Category
import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SortOptions
import dev.koga.hopi.repository.GameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


@OptIn(ExperimentalCoroutinesApi::class)
class CategoryGamesViewModel(
    savedStateHandle: SavedStateHandle,
    repository: GameRepository
) : ViewModel() {

    private val key = savedStateHandle.toRoute<Route.CategoryGames>().categoryKey
    private val category = Category.all.first { category -> category.key == key }
    private val sortOptions = MutableStateFlow(SortOptions(platform = null, order = null))
    private val gamesResource = sortOptions.flatMapLatest {
        repository.getAll(
            category = category,
            sortOptions = it
        )
    }

    val uiState = combine(
        sortOptions,
        gamesResource,
    ) { sortOptions, gamesResource ->
        CategoryGamesUiState(
            category = category,
            sortOptions = sortOptions,
            gamesUiState = gamesResource
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CategoryGamesUiState(
            category = category,
            sortOptions = sortOptions.value,
            gamesUiState = Resource.Loading
        )
    )

    fun onSubmitSortOptions(options: SortOptions) {
        sortOptions.update { options }
    }
}