//
//  Category.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import Shared

typealias Category = Shared.Category

extension Category: Identifiable {
    public var id: String { self.key }
}
