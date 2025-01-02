import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        KoinInit.shared.doInit()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
