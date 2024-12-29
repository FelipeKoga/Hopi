package dev.koga.hopi.feature.games

import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions

data class GamesUiState(
    val sortOptions: SortOptions = SortOptions.empty,
    val data: Resource<List<SimpleGame>>
)