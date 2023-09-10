// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class MainUiPresenterFactory(
  private val presenterFactory: (Navigator) -> MainPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is MainScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class MainPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<MainUiState> {
  @Composable
  override fun present(): MainUiState {
    val scope = rememberCoroutineScope()

    fun eventSink(event: MainUiEvent) {
      when (event) {
        MainUiEvent.OpenItem -> {}
      }
    }

    return MainUiState(
      ::eventSink,
    )
  }
}
