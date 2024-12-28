package dev.koga.hopi

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.koga.hopi.feature.details.GameDetailsScreen
import dev.koga.hopi.feature.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Route.Home
        ) {
            composable<Route.Home> {
                HomeScreen(
                    viewModel = koinViewModel(),
                    onGameClicked = {
                        navController.navigate(Route.GameDetails(it.id))
                    }
                )
            }

            composable<Route.GameDetails> {
                GameDetailsScreen(
                    viewModel = koinViewModel(),
                    onBack = navController::popBackStack
                )
            }
        }
    }
}