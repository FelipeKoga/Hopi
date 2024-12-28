import SwiftUI
import Shared

// Game model
struct Game: Identifiable {
    let id = UUID()
    let title: String
    let imageSystemName: String
    let rating: Double
}

struct ContentView: View {
    // Sample game data
    private let games = [
        Game(title: "Adventure Quest", imageSystemName: "gamecontroller", rating: 4.5),
        Game(title: "Space Explorer", imageSystemName: "airplane", rating: 4.8),
        Game(title: "Puzzle Master", imageSystemName: "puzzle", rating: 4.2),
        Game(title: "Racing Pro", imageSystemName: "car", rating: 4.7),
        Game(title: "Strategy Wars", imageSystemName: "flag", rating: 4.6)
    ]
    
    var body: some View {
        NavigationView {
            ScrollView {
                LazyVGrid(columns: [
                    GridItem(.flexible()),
                    GridItem(.flexible())
                ], spacing: 16) {
                    ForEach(games) { game in
                        GameCard(game: game)
                    }
                }
                .padding()
            }
            .navigationTitle("Games")
        }
    }
}

struct GameCard: View {
    let game: Game
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Image(systemName: game.imageSystemName)
                .font(.system(size: 50))
                .frame(width: 60, height: 60)
                .foregroundColor(.accentColor)
            
            Text(game.title)
                .font(.headline)
                .lineLimit(2)
            
            HStack {
                Image(systemName: "star.fill")
                    .foregroundColor(.yellow)
                Text(String(format: "%.1f", game.rating))
                    .font(.subheadline)
            }
        }
        .padding()
        .background(
            RoundedRectangle(cornerRadius: 12)
                .fill(Color(.systemBackground))
                .shadow(radius: 4)
        )
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
