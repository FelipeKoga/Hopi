package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.koga.hopi.di.KoinInit

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    KoinInit.init()

    CanvasBasedWindow(
        title = "Hopi",
        canvasElementId = "viewport-container",
        applyDefaultStyles = false,
    ) {
        App()
    }
}