//
//  GamesView.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct GamesView : View {
    @Environment(\.colorScheme) var colorScheme
    @State var viewModel: GamesViewModel = get()
    @State private var activeSheet: ActiveSheet? = nil
    
    enum ActiveSheet: Identifiable {
        case categories
        case sortOptions
        case categoryGames(Category)
        case gameDetails(SimpleGame)
        
        var id: Int {
            switch self {
            case .categories: return 0
            case .sortOptions: return 1
            case .categoryGames: return 2
            case .gameDetails: return 3
            }
        }
    }

    var body: some View {
        NavigationView {
            VStack {
                ScrollView {
                    Observing(viewModel.uiState) { uiState in
                        GamesContent(
                            uiState: uiState,
                            onShowCategoriesSheet: { activeSheet = .categories},
                            onShowSortOptionsSheet: {activeSheet = .sortOptions},
                            onShowGameDetailsSheet: { game in activeSheet = .gameDetails(game)}
                        )
                    }
                }
            }
            .preferredColorScheme(.dark)
            .navigationTitle("Games")
            .sheet(item: $activeSheet) { sheet in
                switch sheet {
                case .categories:
                    CategoriesSheet(
                        onClick: { category in
                            activeSheet = .categoryGames(category)
                        }
                    )
                case .sortOptions:
                    SortOptionsSheet(
                        selectedSortOptions: viewModel.uiState.value.sortOptions) { options in
                            viewModel.onSubmitSortOptions(options: options)
                        }
                    
                case .categoryGames(let category):
                    CategoryGamesView(
                        category: category
                    )
                    
                case .gameDetails(let game):
                    GameDetailsView(gameId: game.id)
                }
            }
        }
    }
}

private struct GamesContent: View {
    let uiState: GamesUiState
    let onShowCategoriesSheet: () -> Void
    let onShowSortOptionsSheet: () -> Void
    let onShowGameDetailsSheet: (SimpleGame) -> Void
    
    var body: some View {
        switch onEnum(of: uiState.data) {
        case .loading:
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())

        case .success(let success):
            if let games = success.data as? [SimpleGame] {
                GamesListContent(
                    games: games,
                    sortOptions: uiState.sortOptions,
                    showCategoriesButton: true,
                    onShowCategoriesSheet: onShowCategoriesSheet,
                    onShowSortOptionsSheet: onShowSortOptionsSheet,
                    onGameDetails: onShowGameDetailsSheet
                )
            }
        case .error:
            Text("Error loading games")
        }
    }
}

