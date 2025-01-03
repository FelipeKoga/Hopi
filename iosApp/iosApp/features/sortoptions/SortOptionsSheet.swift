//
//  ShortOptionsSheet.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct SortOptionsSheet: View {
    @Environment(\.dismiss) var dismiss
    
    let selectedSortOptions: SortOptions
    let onSubmit: (SortOptions) -> Void
    
    @State private var selectedPlatform: Platform? = nil
    @State private var selectedOrder: OrderOption? = nil
    
    init(
        selectedSortOptions: SortOptions,
        onSubmit: @escaping (SortOptions) -> Void,
        selectedPlatform: Platform? = nil,
        selectedOrder: OrderOption? = nil
    ) {
        self.selectedSortOptions = selectedSortOptions
        self.onSubmit = onSubmit
        self.selectedPlatform = selectedPlatform
        self.selectedOrder = selectedOrder
    }
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading, spacing: 24) {
                Text("Choose the options to sort and find")
                    .padding()

                VStack(alignment: .leading, spacing: 16) {
                    Text("Platform")
                        .font(.headline)

                    VStack(spacing: 16) {
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
                            isSelected: selectedOrder
                                == OrderOption.releaseDate,
                            action: { selectedOrder = OrderOption.releaseDate }
                        )
                        SortOptionButton(
                            title: "Alphabetical",
                            icon: "textformat",
                            isSelected: selectedOrder
                                == OrderOption.alphabetical,
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
                    onSubmit(options)
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
