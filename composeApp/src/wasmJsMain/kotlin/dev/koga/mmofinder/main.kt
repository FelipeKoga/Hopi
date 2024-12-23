package dev.koga.mmofinder

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(module = dev.koga.mmofinder.feature.home.module)

    ComposeViewport(document.body!!) {
        App()
    }
}