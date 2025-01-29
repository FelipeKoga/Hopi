package dev.koga.hopi

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.koga.hopi.analytics.WasmJSAnalyticsLogger
import dev.koga.hopi.di.KoinInit
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    KoinInit.init()
    KoinInit.loadNativeModules(WasmJSAnalyticsLogger())

    ComposeViewport(document.body!!) {
        App()
    }
}