//
//  FosdemApp.swift
//  Fosdem
//
//  Created by Henry Addo on 5/7/24.
//

import FosdemKt
import SwiftUI

@main
struct FosdemApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            let uiComponent = createMainUiControllerComponent(
                appComponent: delegate.appComponent
            )
            ContentView(component: uiComponent)
        }
    }
}

private func createAppComponent() -> AppComponent {
    return AppComponent.companion.create()
}

private func createMainUiControllerComponent(
    appComponent: AppComponent
) -> MainUiControllerComponent {
    return MainUiControllerComponent.companion.create(
        appComponent: appComponent
    )
}

class AppDelegate: UIResponder, UIApplicationDelegate {
    lazy var appComponent: AppComponent = createAppComponent()
}
