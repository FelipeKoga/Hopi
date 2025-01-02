package dev.koga.hopi.feature.category_games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowLeft
import compose.icons.tablericons.Filter
import dev.koga.hopi.designsystem.components.HopiAssistChip
import dev.koga.hopi.feature.games.components.GameCard
import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.shared_ui.ErrorUI
import dev.koga.hopi.shared_ui.LoadingUI
import dev.koga.hopi.shared_ui.SortOptionsBottomSheet
import dev.koga.hopi.viewmodel.CategoryGamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryGamesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryGamesViewModel,
    onGameClicked: (SimpleGame) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var openSortOptionsBottomSheet by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    if (openSortOptionsBottomSheet) {
        SortOptionsBottomSheet(
            sortOptions = uiState.sortOptions,
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
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(TablerIcons.ArrowLeft, "back")
                    }
                },
                title = {
                    Column {
                        Text(
                            text = uiState.category.label,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HopiAssistChip(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        label = "Sort by",
                        leadingIcon = {
                            Icon(
                                imageVector = TablerIcons.Filter,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        trailingIcon = {
                            if (uiState.sortOptions.count > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ) {
                                    Text(
                                        text = uiState.sortOptions.count.toString(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                        )
                                    )
                                }
                            }
                        },
                        onClick = { openSortOptionsBottomSheet = true }
                    )
                }
            }

            when (uiState.gamesUiState) {
                Resource.Error -> item {
                    ErrorUI(
                        modifier = Modifier.fillParentMaxSize(),
                        onTryAgain = {}
                    )
                }

                Resource.Loading -> item {
                    LoadingUI(modifier = Modifier.fillParentMaxSize())
                }

                is Resource.Success -> items(
                    (uiState.gamesUiState as? Resource.Success)?.data ?: emptyList()
                ) { game ->
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