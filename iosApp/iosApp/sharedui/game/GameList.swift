//
//  GameList.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct GamesListContent: View {
    let games: [SimpleGame]
    let sortOptions: SortOptions

    let showCategoriesButton: Bool
    let onShowCategoriesSheet: () -> Void
    let onShowSortOptionsSheet: () -> Void
    
    var body: some View {
        VStack {
            HStack {
                if showCategoriesButton {
                    Button(action: onShowCategoriesSheet) {
                        HStack {
                            Image(systemName: "gamecontroller")
                            Text("Browse categories")
                        }
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                        .background(Color(.secondarySystemBackground))
                        .cornerRadius(20)
                    }
                    
                    Spacer()
                }

                Button(action: onShowSortOptionsSheet) {
                    HStack {
                        Image(systemName: "arrow.up.arrow.down")
                        Text("Sort by")
                        if sortOptions.count > 0 {
                            Text("\(sortOptions.count)")
                                .foregroundColor(.white)
                                .padding(6)
                                .background(Color.primaryGreen)
                                .clipShape(Circle())
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 8)
                    .background(Color(.secondarySystemBackground))
                    .cornerRadius(20)
                }
            }
            .padding()
            
            LazyVStack(spacing: 16) {
                ForEach(games, id: \.id) { game in
                    GameCard(game: game)
                }
            }
            .padding()
        }
    }
}
