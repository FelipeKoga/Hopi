package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = dev.koga.hopi.feature.home.module)

    CanvasBasedWindow(
        title = "Hopi",
        canvasElementId = "viewport-container",
        applyDefaultStyles = false,
    ) {
        App()
    }
}