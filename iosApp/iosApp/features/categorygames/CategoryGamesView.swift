//
//  CategoryGamesView.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct CategoryGamesView: View {
    @Environment(\.dismiss) var dismiss
    @State var viewModel: CategoryGamesViewModel
    @State private var activeSheet: ActiveSheet? = nil

    enum ActiveSheet: Identifiable {
        case gameDetails(SimpleGame)
        
        var id: Int {
            switch self {
            case .gameDetails: return 1
            }
        }
    }
    
    init(category: Category) {
        self.viewModel = get(CategoryGamesViewModel.self, parameter: category.key)
    }
    
    var body: some View {
        NavigationView {
            VStack {
                ScrollView {
                    Observing(viewModel.uiState) { uiState in
                        GamesContent(
                            uiState: uiState,
                            onShowSortOptionsSheet: {},
                            onShowGameDetailsSheet: { game in activeSheet = .gameDetails(game) }
                        )
                    }
                }
            }
            .navigationTitle(viewModel.uiState.value.category.label)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button(action: { dismiss() }) {
                        Image(systemName: "xmark")
                    }
                }
            }
        }
        .sheet(item: $activeSheet) { sheet in
            switch sheet {
            case .gameDetails(let game):
                GameDetailsView(gameId: game.id)
            }
        }
    }
}

private struct GamesContent: View {
    let uiState: CategoryGamesUiState
    let onShowSortOptionsSheet: () -> Void
    let onShowGameDetailsSheet: (SimpleGame) -> Void

    var body: some View {
        switch onEnum(of: uiState.gamesUiState) {
        case .loading:
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())

        case .success(let success):
            if let games = success.data as? [SimpleGame] {
                GamesListContent(
                    games: games,
                    sortOptions: uiState.sortOptions,
                    showCategoriesButton: false,
                    onShowCategoriesSheet: {},
                    onShowSortOptionsSheet: {},
                    onGameDetails: onShowGameDetailsSheet
                )

            }
        case .error:
            Text("Error loading games")
        }
    }
}


