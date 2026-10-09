import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        KoinApp_iosKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
