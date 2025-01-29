@file:OptIn(KoinExperimentalAPI::class)

package dev.koga.hopi

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.koga.hopi.designsystem.HopiTheme
import dev.koga.hopi.feature.categorygames.CategoryGamesScreen
import dev.koga.hopi.feature.gamedetails.GameDetailsScreen
import dev.koga.hopi.feature.games.GamesScreen
import dev.koga.hopi.viewmodel.CategoryGamesViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    HopiTheme {
        NavHost(
            navController = navController,
            startDestination = Route.Games,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 500)
                )
            },
        ) {
            composable<Route.Games> {
                GamesScreen(
                    viewModel = koinNavViewModel(),
                    onGameClicked = {
                        navController.navigate(Route.GameDetails(it.id))
                    },
                    onCategoryClicked = {
                        navController.navigate(Route.CategoryGames(it.key))
                    }
                )
            }

            composable<Route.GameDetails> { backStackEntry ->
                val gameId = backStackEntry.toRoute<Route.GameDetails>().id
                GameDetailsScreen(
                    viewModel = koinNavViewModel(
                        parameters = { parametersOf(gameId) }
                    ),
                    onBack = navController::popBackStack
                )
            }

            composable<Route.CategoryGames> { backStackEntry ->
                CategoryGamesScreen(
                    viewModel = koinViewModel<CategoryGamesViewModel>(
                        parameters = { parametersOf(backStackEntry.toRoute()) }
                    ),
                    onBack = navController::popBackStack,
                    onGameClicked = {
                        navController.navigate(Route.GameDetails(it.id))
                    }
                )
            }
        }
    }
}