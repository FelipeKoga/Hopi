//
//  AnalyticsLogger.swift
//  iosApp
//
//  Created by Felipe Koga on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Shared

class IOSAnalyticsLogger : AnalyticsLogger {
    let repository: GameRepository = get()
    
    func log(event: String) {
        print(event)
    }
}
