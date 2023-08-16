// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
  /*val appComponent = remember {
    AppComponent.create()
  }*/

  Window(
    title = "FOSDEM",
    onCloseRequest = ::exitApplication,
  ) {
    /*val component = remember(appComponent) {
      WindowComponent.create(appComponent)
    }

    val backstack = rememberSaveableBackStack { push(MainScreen) }
    val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }
    component.mainContent(
      backstack,
      navigator,
      Modifier,
    )*/
  }
}
