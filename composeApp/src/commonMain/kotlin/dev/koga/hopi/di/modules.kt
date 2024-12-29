package dev.koga.hopi.di

import dev.koga.hopi.feature.category_games.CategoryGamesViewModel
import dev.koga.hopi.feature.game_details.GameDetailsViewModel
import dev.koga.hopi.feature.games.GamesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::GamesViewModel)
    viewModelOf(::GameDetailsViewModel)
    viewModelOf(::CategoryGamesViewModel)
}