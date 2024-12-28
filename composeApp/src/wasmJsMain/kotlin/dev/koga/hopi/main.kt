package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = dev.koga.hopi.feature.home.module)

    ComposeViewport(document.body!!) {
        App()
    }
}