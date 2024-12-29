package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.koga.hopi.di.viewModelModule
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = viewModelModule)

    ComposeViewport(document.body!!) {
        App()
    }
}