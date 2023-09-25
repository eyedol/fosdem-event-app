// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
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
      is SessionDetailScreen -> presenterFactory(navigator)
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
        SessionDetailUiEvent.GoToSession -> TODO()
        is SessionDetailUiEvent.RegisterSessionToCalendar -> TODO()
        is SessionDetailUiEvent.ShareSession -> TODO()
        is SessionDetailUiEvent.ToggleSessionBookmark -> TODO()
      }
    }

    // TODO load session types
    return SessionDetailUiState(
      sessionDetailScreenUiState = ScreenDetailScreenUiState.Loading,
      eventSink = ::eventSink,
    )
  }
}
