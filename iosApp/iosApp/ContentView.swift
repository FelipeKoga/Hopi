import SwiftUI
import Shared

typealias Category = Shared.Category

extension Color {
    static let darkBackground = Color(red: 18/255, green: 18/255, blue: 18/255)  // #121212
    static let surfaceDark = Color(red: 30/255, green: 30/255, blue: 30/255)     // #1E1E1E
    static let primaryGreen = Color(red: 0/255, green: 200/255, blue: 83/255)    // #00C853
}

extension Category: Identifiable {
    public var id: String { self.key }
}

func get<A: AnyObject>() -> A {
    return KoinInit.shared.koin.get(objCClass: A.self) as! A
}

func get<A: AnyObject>(_ type: A.Type) -> A {
    return KoinInit.shared.koin.get(objCClass: A.self) as! A
}

func get<A: AnyObject>(_ type: A.Type, qualifier: (any Koin_coreQualifier)? = nil, parameter: Any) -> A {
    return KoinInit.shared.koin.get(objCClass: A.self, qualifier: qualifier, parameter: parameter) as! A
}


struct ContentView: View {
    @Environment(\.colorScheme) var colorScheme
    @State var viewModel: GamesViewModel = get()
    @State private var activeSheet: ActiveSheet? = nil
    @State private var selectedCategory: Category? = nil
    
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
                            showingCategories: { activeSheet = .categories },
                            showingSortOptions: { activeSheet = .sortOptions },
                            viewModel: viewModel
                        )
                    }
                }
            }
            .background(Color.darkBackground)
            .preferredColorScheme(.dark)
            .navigationTitle("Games")
            .sheet(item: $activeSheet) { sheet in
                switch sheet {
                case .categories:
                    CategoriesSheet(
                        viewModel: $viewModel,
                        selectedCategory: { category in
                            activeSheet = .categoryGames(category)
                        }
                    )
                case .sortOptions:
                    SortOptionsSheet(viewModel: $viewModel)
                case .categoryGames(let category):
                    CategoryGamesView(
                        category: category,
                        count: viewModel.uiState.value.sortOptions.count
                    )
                }
            }
        }
    }
}

struct GamesContent: View {
    let uiState: GamesUiState
    let showingCategories: () -> Void
    let showingSortOptions: () -> Void
    let viewModel: GamesViewModel
    
    var body: some View {
        switch onEnum(of: uiState.data) {
        case .loading:
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle())
        case .success(let success):
            if let games = success.data as? [SimpleGame] {
                GamesListContent(
                    games: games,
                    showingCategories: showingCategories,
                    showingSortOptions: showingSortOptions,
                    sortOptionsCount: uiState.sortOptions.count,
                    viewModel: viewModel
                )
            }
        case .error:
            Text("Error loading games")
        }
    }
}

struct GamesListContent: View {
    let games: [SimpleGame]
    let showingCategories: () -> Void
    let showingSortOptions: () -> Void
    let sortOptionsCount: Int32
    let viewModel: GamesViewModel
    
    var body: some View {
        VStack {
            HStack {
                Button(action: showingCategories) {
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
                
                Button(action: showingSortOptions) {
                    HStack {
                        Image(systemName: "arrow.up.arrow.down")
                        Text("Sort by")
                        if sortOptionsCount > 0 {
                            Text("\(sortOptionsCount)")
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

struct GameCard: View {
    let game: SimpleGame
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            // Game Image
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

struct CategoryButton: View {
    let category: Category
    @Environment(\.dismiss) var dismiss
    @Binding var viewModel: GamesViewModel
    let selectedCategory: (Category) -> Void
    
    var body: some View {
        Button(action: {
            selectedCategory(category)
            dismiss()
        }) {
            Text(category.label)
                .foregroundColor(Color.primaryGreen)
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .frame(maxWidth: .infinity)
                .background(Color.surfaceDark)
                .cornerRadius(8)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.primaryGreen, lineWidth: 1)
                )
        }
    }
}

struct CategoriesSheet: View {
    @Environment(\.dismiss) var dismiss
    @Binding var viewModel: GamesViewModel
    let selectedCategory: (Category) -> Void
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading) {
                    Text("Choose a category and browse new games!")
                        .padding()
                    
                    LazyVGrid(columns: columns, spacing: 16) {
                        ForEach(Category.companion.all, id: \.key) { category in
                            CategoryButton(
                                category: category,
                                viewModel: $viewModel,
                                selectedCategory: { category in
                                    selectedCategory(category)
                                }
                            )
                        }
                    }
                    .padding()
                }
            }
            .navigationTitle("Categories")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct SortOptionsSheet: View {
    @Environment(\.dismiss) var dismiss
    @Binding var viewModel: GamesViewModel
    @State private var selectedPlatform: Platform? = nil
    @State private var selectedOrder: OrderOption? = nil
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading, spacing: 24) {
                Text("Choose the options to sort and find")
                    .padding()
                
                VStack(alignment: .leading, spacing: 16) {
                    Text("Platform")
                        .font(.headline)
                    
                    HStack(spacing: 16) {
                        SortOptionButton(
                            title: "PC",
                            icon: "desktopcomputer",
                            isSelected: selectedPlatform == Platform.pc,
                            action: { selectedPlatform = Platform.pc }
                        )
                        SortOptionButton(
                            title: "Browser",
                            icon: "globe",
                            isSelected: selectedPlatform == Platform.browser,
                            action: { selectedPlatform = Platform.browser }
                        )
                        SortOptionButton(
                            title: "All",
                            icon: "square.grid.2x2",
                            isSelected: selectedPlatform == Platform.all,
                            action: { selectedPlatform = Platform.all }
                        )
                    }
                }
                .padding()
                
                VStack(alignment: .leading, spacing: 16) {
                    Text("Order by")
                        .font(.headline)
                    
                    VStack(spacing: 16) {
                        SortOptionButton(
                            title: "Release date",
                            icon: "calendar",
                            isSelected: selectedOrder == OrderOption.releaseDate,
                            action: { selectedOrder =  OrderOption.releaseDate }
                        )
                        SortOptionButton(
                            title: "Alphabetical",
                            icon: "textformat",
                            isSelected: selectedOrder == OrderOption.alphabetical,
                            action: { selectedOrder = OrderOption.alphabetical }
                        )
                        SortOptionButton(
                            title: "Relevance",
                            icon: "star",
                            isSelected: selectedOrder == OrderOption.relevance,
                            action: { selectedOrder = OrderOption.relevance }
                        )
                    }
                }
                .padding()
                
                Spacer()
                
                Button(action: {
                    let options = SortOptions(platform: selectedPlatform, order: selectedOrder)
                    viewModel.onSubmitSortOptions(options: options)
                    dismiss()
                }) {
                    Text("Submit")
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.primaryGreen)
                        .cornerRadius(8)
                }
                .padding()
            }
            .navigationTitle("Sort by")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct SortOptionButton: View {
    let title: String
    let icon: String
    let isSelected: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack {
                Image(systemName: icon)
                Text(title)
            }
            .foregroundColor(isSelected ? .white : .gray)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
            .frame(maxWidth: .infinity)
            .background(isSelected ? Color.primaryGreen : Color.surfaceDark)
            .cornerRadius(8)
        }
    }
}

struct CategoryGamesView: View {
    let category: Category
    let count: Int32
    @State var viewModel: CategoryGamesViewModel
    @Environment(\.dismiss) var dismiss
    
    init(category: Category, count: Int32) {
        self.category = category
        self.count = count
        self.viewModel = get(CategoryGamesViewModel.self, parameter: category.key)
    }
    
    var body: some View {
        NavigationView {
            VStack {
                // Top buttons for sort options
                Button(action: { /* Show sort options */ }) {
                    HStack {
                        Image(systemName: "arrow.up.arrow.down")
                        Text("Sort by")
                        if count > 0 {
                            Text("\(count)")
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
                .padding()
                
                // Games list
                ScrollView {
                    Observing(viewModel.uiState) { uiState in
                        switch onEnum(of: uiState.gamesUiState) {
                        case .loading:
                            ProgressView()
                        case .success(let success):
                            if let games = success.data as? [SimpleGame] {
                                LazyVStack(spacing: 16) {
                                    ForEach(games, id: \.id) { game in
                                        GameCard(game: game)
                                    }
                                }
                                .padding()
                            }
                        case .error:
                            Text("Error loading games")
                        }
                    }
                }
            }
            .navigationTitle(category.label)
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

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
