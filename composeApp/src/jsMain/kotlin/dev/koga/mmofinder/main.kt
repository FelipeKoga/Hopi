package dev.koga.mmofinder

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = dev.koga.mmofinder.feature.home.module)

    CanvasBasedWindow(
        title = "MMO Finder",
        canvasElementId = "viewport-container",
        applyDefaultStyles = false,
    ) {
        App()
    }
}