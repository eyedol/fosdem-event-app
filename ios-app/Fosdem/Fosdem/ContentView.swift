//
//  ContentView.swift
//  Fosdem
//
//  Created by Henry Addo on 5/7/24.
//

import FosdemKt
import SwiftUI

struct ContentView: View {
    private let component: MainUiControllerComponent

    init(component: MainUiControllerComponent) {
        self.component = component
    }
    var body: some View {
        ComposeView(component: self.component)
            .ignoresSafeArea(.all, edges: .all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    private let component: MainUiControllerComponent

    init(component: MainUiControllerComponent) {
        self.component = component
    }

    func makeUIViewController(context _: Context) -> UIViewController {
        return component.uiViewControllerFactory()
    }

    func updateUIViewController(_: UIViewController, context _: Context) {}
}
