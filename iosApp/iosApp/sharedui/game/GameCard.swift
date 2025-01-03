//
//  GameCard.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct GameCard: View {
    let game: SimpleGame
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            AsyncImage(url: URL(string: game.thumbnail)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Rectangle()
                    .fill(Color.gray.opacity(0.2))
            }
            .frame(height: 150)
            .clipped()
            
            // Game Info
            VStack(alignment: .leading, spacing: 8) {
                Text(game.title)
                    .font(.headline)
                
                Text(game.description_)
                    .font(.subheadline)
                    .foregroundColor(.gray)
                    .lineLimit(2)
                
                HStack(spacing: 12) {
                    TagView(text: game.genre, icon: "gamecontroller")
                    TagView(text: game.platform, icon: "desktopcomputer")
                    TagView(text: game.releaseDate, icon: "calendar")
                }
            }
            .padding()
        }
        .background(Color.surfaceDark)
        .cornerRadius(12)
        .shadow(radius: 4)
    }
}

struct TagView: View {
    let text: String
    let icon: String
    
    var body: some View {
        HStack(spacing: 4) {
            Image(systemName: icon)
            Text(text)
        }
        .font(.caption)
        .foregroundColor(.green)
    }
}
