package dev.koga.hopi.feature.games

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.TablerIcons
import compose.icons.tablericons.DeviceGamepad
import compose.icons.tablericons.Filter
import dev.koga.hopi.designsystem.components.HopiAssistChip
import dev.koga.hopi.feature.games.bottomsheet.CategoriesBottomSheet
import dev.koga.hopi.shared_ui.SortOptionsBottomSheet
import dev.koga.hopi.feature.games.components.GameCard
import dev.koga.hopi.model.Category
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions
import dev.koga.hopi.shared_ui.ErrorUI
import dev.koga.hopi.shared_ui.LoadingUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(
    modifier: Modifier = Modifier,
    viewModel: GamesViewModel,
    onGameClicked: (SimpleGame) -> Unit,
    onCategoryClicked: (Category) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var openCategoriesBottomSheet by remember { mutableStateOf(false) }
    var openSortOptionsBottomSheet by remember { mutableStateOf(false) }

    if (openCategoriesBottomSheet) {
        CategoriesBottomSheet(
            onDismissRequest = {
                openCategoriesBottomSheet = false
            },
            onClick = {
                openCategoriesBottomSheet = false
                onCategoryClicked(it)
            }
        )
    }

    if (openSortOptionsBottomSheet) {
        SortOptionsBottomSheet(
            onDismissRequest = {
                openSortOptionsBottomSheet = false
            },
            onSubmit = {
                openSortOptionsBottomSheet = false
                viewModel.onSubmitSortOptions(it)
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Column {
                        Text(
                            text = "Games",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Discover the best games available right now",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = uiState,
            contentKey = { uiState::class },
            modifier = Modifier.padding(contentPadding)
        ) { target ->
            when (target) {
                GamesUiState.Error -> ErrorUI(onTryAgain = {})

                GamesUiState.Loading -> LoadingUI()

                is GamesUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            GamesFilterOptions(
                                sortOptions = target.sortOptions,
                                onBrowseCategories = { openCategoriesBottomSheet = true },
                                onOpenSortOptions = { openSortOptionsBottomSheet = true }
                            )
                        }

                        items(target.games) { game ->
                            GameCard(
                                modifier = modifier,
                                game = game,
                                onClick = { onGameClicked(game) }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun GamesFilterOptions(
    modifier: Modifier = Modifier,
    onBrowseCategories: () -> Unit,
    onOpenSortOptions: () -> Unit,
    sortOptions: SortOptions = SortOptions.empty
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        HopiAssistChip(
            label = "Browse categories",
            leadingIcon = {
                Icon(
                    imageVector = TablerIcons.DeviceGamepad,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }, onClick = onBrowseCategories
        )

        HopiAssistChip(
            label = "Sort by",
            leadingIcon = {
                Icon(
                    imageVector = TablerIcons.Filter,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            trailingIcon = {
                if (sortOptions.count > 0) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text(
                            text = sortOptions.count.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            },
            onClick = onOpenSortOptions
        )
    }
}