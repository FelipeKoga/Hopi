import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        KoinInit.shared.doInit()
        KoinInit.shared.loadNativeModules(analyticsLogger: IOSAnalyticsLogger())
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
