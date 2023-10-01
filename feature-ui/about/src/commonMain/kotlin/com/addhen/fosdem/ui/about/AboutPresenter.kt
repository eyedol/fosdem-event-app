// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class AboutUiPresenterFactory(
  private val presenterFactory: (Navigator) -> AboutPresenter,
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
class AboutPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<AboutUiState> {
  @Composable
  override fun present(): AboutUiState {
    // val scope = rememberCoroutineScope()

    fun eventSink(event: AboutUiEvent) {
      when (event) {
        is AboutUiEvent.GoToAboutItem -> TODO()
        is AboutUiEvent.GoToLink -> TODO()
        AboutUiEvent.GoToSession -> TODO()
      }
    }

    // TODO load session types
    return AboutUiState(
      versionName = "1.0.0",
      eventSink = ::eventSink,
    )
  }
}
