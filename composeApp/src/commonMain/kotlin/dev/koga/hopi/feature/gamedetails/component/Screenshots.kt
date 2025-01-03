package dev.koga.hopi.feature.gamedetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.koga.hopi.model.Screenshot


@Composable
fun ScreenshotsUI(modifier: Modifier = Modifier, screenshots: List<Screenshot>) {
    var selected by remember {
        mutableStateOf(screenshots.first())
    }

    Column(modifier = modifier) {
        AsyncImage(
            model = selected.image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 196.dp)
                .clip(RoundedCornerShape(12.dp)),
            clipToBounds = false,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            screenshots.forEach { item ->
                key(item.id) {
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 72.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (selected == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selected = item }
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            model = item.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                        )
                    }
                }
            }
        }
    }
}