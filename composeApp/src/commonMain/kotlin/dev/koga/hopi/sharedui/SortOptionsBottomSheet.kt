package dev.koga.hopi.sharedui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.ChartArrowsVertical
import compose.icons.tablericons.DevicesPc
import compose.icons.tablericons.SortAscending
import compose.icons.tablericons.World
import dev.koga.hopi.designsystem.components.HopiFilterChip
import dev.koga.hopi.model.OrderOption
import dev.koga.hopi.model.Platform
import dev.koga.hopi.model.SortOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortOptionsBottomSheet(
    sortOptions: SortOptions,
    onDismissRequest: () -> Unit,
    onSubmit: (SortOptions) -> Unit,
) {
    var selectedPlatform by remember { mutableStateOf(sortOptions.platform) }
    var selectedOrder by remember { mutableStateOf(sortOptions.order) }

    fun updateOrderOption(orderOption: OrderOption) {
        selectedOrder = if (orderOption == selectedOrder) null else orderOption
    }

    fun updatePlatformOption(platform: Platform) {
        selectedPlatform = if (platform == selectedPlatform) null else platform
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose the options to sort and find ",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Platform",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            PlatformOptions(
                selected = selectedPlatform,
                onClick = ::updatePlatformOption
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Order by",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OrderOptions(
                selected = selectedOrder,
                onClick = ::updateOrderOption
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onSubmit(
                        SortOptions(
                            platform = selectedPlatform,
                            order = selectedOrder
                        )
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(.5f)
            ) {
                Text(text = "Submit")
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PlatformOptions(
    modifier: Modifier = Modifier,
    selected: Platform?,
    onClick: (Platform) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Platform.entries.forEach { platform ->
            when (platform) {
                Platform.PC -> HopiFilterChip(
                    selected = selected == platform,
                    label = "PC",
                    trailingIcon = {
                        Icon(
                            imageVector = TablerIcons.DevicesPc,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    onClick = { onClick(platform) }
                )

                Platform.BROWSER -> HopiFilterChip(
                    selected = selected == platform,
                    label = "Browser",
                    trailingIcon = {
                        Icon(
                            imageVector = TablerIcons.World,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    onClick = { onClick(platform) }
                )

                Platform.ALL -> HopiFilterChip(
                    selected = selected == platform,
                    label = "All",
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = TablerIcons.DevicesPc,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Icon(
                                imageVector = TablerIcons.World,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    onClick = { onClick(platform) }
                )
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OrderOptions(
    modifier: Modifier = Modifier,
    selected: OrderOption?,
    onClick: (OrderOption) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OrderOption.entries.forEach { orderOption ->
            when (orderOption) {
                OrderOption.RELEASE_DATE -> HopiFilterChip(
                    selected = selected == orderOption,
                    label = "Release date",
                    trailingIcon = {
                        Icon(
                            imageVector = TablerIcons.Calendar,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    onClick = { onClick(orderOption) }
                )

                OrderOption.ALPHABETICAL -> HopiFilterChip(
                    selected = selected == orderOption,
                    label = "Alphabetical",
                    trailingIcon = {
                        Icon(
                            imageVector = TablerIcons.SortAscending,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    onClick = { onClick(orderOption) }
                )

                OrderOption.RELEVANCE -> HopiFilterChip(
                    selected = selected == orderOption,
                    label = "Relevance",
                    trailingIcon = {
                        Icon(
                            imageVector = TablerIcons.ChartArrowsVertical,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    onClick = { onClick(orderOption) }
                )
            }

        }
    }
}