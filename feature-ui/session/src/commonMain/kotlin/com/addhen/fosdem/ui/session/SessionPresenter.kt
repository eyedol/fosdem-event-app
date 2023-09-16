// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<SessionUiState> {
  @Composable
  override fun present(): SessionUiState {
    val scope = rememberCoroutineScope()

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        SessionUiEvent.OpenItem -> {}
      }
    }

    return SessionUiState(
      ::eventSink,
    )
  }
}
