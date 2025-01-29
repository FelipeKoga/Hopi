//
//  GameDetailsSheet.swift
//  iosApp
//
//  Created by Felipe Koga on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct GameDetailsView: View {
    @Environment(\.dismiss) var dismiss
    @State var viewModel: GameDetailsViewModel
    
    init(gameId: Int32) {
        self.viewModel = get(GameDetailsViewModel.self, parameter: gameId)
    }
    
    var body: some View {
        Group {
            Observing(viewModel.gameState) { uiState in
                switch onEnum(of: uiState) {
                case .loading: LoadingView()

                case .success(let success): GameDetailsContent(
                    game: success.game
                )

                case .error: ErrorView(onRetry: {
                    // Add retry functionality if needed
                })
                }
            }
        }
    }
}

private struct GameDetailsContent : View {
    let game: GameDetails
    @State private var isDescriptionExpanded = false
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                // Header Image
                AsyncImage(url: URL(string: game.thumbnail)) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                } placeholder: {
                    Rectangle()
                        .foregroundColor(.gray.opacity(0.2))
                }
                .frame(maxWidth: .infinity)
                .frame(height: 180)
                .clipped()
                
                VStack(alignment: .leading, spacing: 16) {
                    // Title and Live Badge
                    HStack {
                        Text(game.title)
                            .font(.title2)
                            .fontWeight(.bold)
                        
                        Spacer()
                        
                        Text("Live")
                            .font(.caption)
                            .fontWeight(.semibold)
                            .foregroundColor(.green)
                            .padding(.horizontal, 12)
                            .padding(.vertical, 4)
                            .background(Color.green.opacity(0.2))
                            .cornerRadius(16)
                    }
                    
                    // Modified Description Section
                    VStack(alignment: .leading, spacing: 8) {
                        Text(game.normalizedDescription)
                            .font(.body)
                            .foregroundColor(.secondary)
                            .lineLimit(isDescriptionExpanded ? nil : 4)
                        
                        Button(action: {
                            withAnimation {
                                isDescriptionExpanded.toggle()
                            }
                        }) {
                            Text(isDescriptionExpanded ? "Show Less" : "more")
                                .font(.subheadline)
                                .foregroundColor(.green)
                        }
                    }
                    
                    // Game Info Grid
                    LazyVGrid(columns: [
                        GridItem(.flexible()),
                        GridItem(.flexible())
                    ], spacing: 16) {
                        InfoCard(title: "Genre", value: game.genre)
                        InfoCard(title: "Platform", value: game.platform)
                        InfoCard(title: "Release Date", value: game.releaseDate)
                        InfoCard(title: "Developer", value: game.developer)
                    }
                    
                    // Screenshots Section
                    if !game.screenshots.isEmpty {
                        Screenshots(screenshots: game.screenshots)
                    }
                    
                    RequirementsView(requirements: game.minimumSystemRequirements)
                }
                .padding(.horizontal, 24)
            }
        }
        .background(Color(UIColor.systemBackground))
    }
    
}

struct Screenshots : View {
    let screenshots: [Screenshot]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Screenshots")
                .font(.title3)
                .fontWeight(.bold)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    ForEach(screenshots, id: \.self) { screenshot in
                        AsyncImage(
                            url: URL(string: screenshot.image)
                        ) { image in
                            image
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                        } placeholder: {
                            Rectangle()
                                .fill(Color.gray.opacity(0.2))
                        }
                        .frame(width: 280, height: 160)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                }
                .padding(.horizontal, 1)
            }
        }
    }
}

struct RequirementsView : View {
    let requirements: MinimumSystemRequirements
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Minimum System Requirements")
                .font(.title3)
                .fontWeight(.bold)
            
            VStack(spacing: 16) {
                SystemRequirementRow(title: "Operating System", value: requirements.os)
                SystemRequirementRow(title: "Processor", value: requirements.processor)
                SystemRequirementRow(title: "Memory", value: requirements.memory)
                SystemRequirementRow(title: "Graphics", value: requirements.graphics)
                SystemRequirementRow(title: "Storage", value: requirements.storage)
            }
        }
    }
}

struct LoadingView: View {
    var body: some View {
        VStack {
            ProgressView()
            Text("Loading...")
                .foregroundColor(.secondary)
        }
    }
}

struct ErrorView: View {
    let onRetry: () -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: "exclamationmark.triangle")
                .font(.system(size: 50))
                .foregroundColor(.red)
            
            Text("Something went wrong")
                .font(.headline)
            
            Button("Try Again") {
                onRetry()
            }
            .buttonStyle(.bordered)
        }
    }
}

struct InfoCard: View {
    let title: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.subheadline)
                .foregroundColor(.secondary)
            Text(value)
                .font(.body)
                .fontWeight(.semibold)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(16)
        .background(Color(UIColor.secondarySystemBackground))
        .cornerRadius(12)
    }
}

struct SystemRequirementRow: View {
    let title: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.subheadline)
                .foregroundColor(.secondary)
            Text(value)
                .font(.body)
                .fontWeight(.semibold)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(16)
        .background(Color(UIColor.secondarySystemBackground))
        .cornerRadius(12)
    }
}

#Preview {
    GameDetailsView(gameId: 1)
}

