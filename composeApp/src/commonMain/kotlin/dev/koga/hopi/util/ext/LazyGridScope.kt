package dev.koga.hopi.util.ext

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable


fun LazyGridScope.fullLine(
    content: @Composable () -> Unit
) {
    return item(
        span = { GridItemSpan(maxLineSpan) }
    ) { content() }
}