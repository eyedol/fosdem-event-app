// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

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
class SessionDetailUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionDetailPresenter,
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
class SessionDetailPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<SessionDetailUiState> {
  @Composable
  override fun present(): SessionDetailUiState {
    val scope = rememberCoroutineScope()

    fun eventSink(event: SessionDetailUiEvent) {
      when (event) {
        is SessionDetailUiEvent.GoToSessionDetails -> TODO()
        SessionDetailUiEvent.SearchSession -> TODO()
        is SessionDetailUiEvent.ToggleSessionBookmark -> TODO()
        SessionDetailUiEvent.ToggleSessionUi -> TODO()
      }
    }

    return SessionDetailUiState(
      eventSink = ::eventSink,
    )
  }
}
