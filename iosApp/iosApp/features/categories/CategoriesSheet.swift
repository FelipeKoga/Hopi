//
//  CategoriesSheet.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CategoriesSheet: View {
    @Environment(\.dismiss) var dismiss
    let onClick: (Category) -> Void

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
                                onClick: onClick
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

struct CategoryButton: View {
    @Environment(\.dismiss) var dismiss
    
    let category: Category
    let onClick: (Category) -> Void
    
    var body: some View {
        Button(action: {
            onClick(category)
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

