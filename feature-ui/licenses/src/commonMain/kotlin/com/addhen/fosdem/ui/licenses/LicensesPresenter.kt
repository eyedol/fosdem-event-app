// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.core.api.screens.LicensesScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class LicensesUiPresenterFactory(
  private val presenterFactory: (Navigator) -> LicensesPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is LicensesScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class LicensesPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<LicensesUiState> {
  @Composable
  override fun present(): LicensesUiState {
    fun eventSink(event: LicensesUiEvent) {
      when (event) {
        is LicensesUiEvent.GoToLink -> navigator.goTo(UrlScreen(event.url))
      }
    }

    // TODO load session types
    return LicensesUiState(
      versionName = "1.0.0",
      eventSink = ::eventSink,
    )
  }
}
