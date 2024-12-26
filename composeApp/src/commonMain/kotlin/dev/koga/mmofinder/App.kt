package dev.koga.mmofinder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.koga.mmofinder.feature.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen(
            viewModel = koinViewModel()
        )
    }
}