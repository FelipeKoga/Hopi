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
                            onShowSortOptionsSheet: {
                            
                            }
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
    }
}

private struct GamesContent: View {
    let uiState: CategoryGamesUiState
    let onShowSortOptionsSheet: () -> Void
    
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
                    onShowSortOptionsSheet: {}
                )

            }
        case .error:
            Text("Error loading games")
        }
    }
}


