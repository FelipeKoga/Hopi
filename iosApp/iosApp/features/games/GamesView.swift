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
        
        var id: Int {
            switch self {
            case .categories: return 0
            case .sortOptions: return 1
            case .categoryGames: return 2
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
                            onShowCategoriesSheet: { activeSheet = .categories },
                            onShowSortOptionsSheet: { activeSheet = .sortOptions }
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
                }
            }
        }
    }
}

private struct GamesContent: View {
    let uiState: GamesUiState
    let onShowCategoriesSheet: () -> Void
    let onShowSortOptionsSheet: () -> Void
    
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
                    onShowSortOptionsSheet: onShowSortOptionsSheet
                )
            }
        case .error:
            Text("Error loading games")
        }
    }
}

