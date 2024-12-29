package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.koga.hopi.di.viewModelModule

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = viewModelModule)

    CanvasBasedWindow(
        title = "Hopi",
        canvasElementId = "viewport-container",
        applyDefaultStyles = false,
    ) {
        App()
    }
}