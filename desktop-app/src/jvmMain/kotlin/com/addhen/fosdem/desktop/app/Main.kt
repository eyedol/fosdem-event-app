// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.desktop.app.di.AppComponent
import com.addhen.fosdem.desktop.app.di.WindowComponent
import com.addhen.fosdem.desktop.app.di.create
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator

fun main() = application {
  val appComponent = remember {
    AppComponent.create()
  }

  Window(
    title = "FOSDEM",
    onCloseRequest = ::exitApplication,
  ) {
    val component = remember(appComponent) {
      WindowComponent.create(appComponent)
    }

    val backstack = rememberSaveableBackStack { push(SessionScreen) }
    val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }
    component.mainContent(
      backstack,
      navigator,
      Modifier,
    )
  }
}
