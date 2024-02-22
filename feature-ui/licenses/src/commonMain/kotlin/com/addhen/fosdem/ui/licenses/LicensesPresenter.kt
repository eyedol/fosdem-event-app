// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.addhen.fosdem.core.api.screens.LicensesScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.data.licenses.api.repository.LicensesRepository
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
  private val licensesRepository: LicensesRepository,
) : Presenter<LicensesUiState> {
  @Composable
  override fun present(): LicensesUiState {
    val licenses by produceState(emptyList()) {
      val licenses = licensesRepository.getLicenses()
      value = licenses.groupBy { it.groupId }
        .map { (groupId, artifacts) ->
          LicenseGroup(
            id = groupId,
            artifacts = artifacts.sortedBy { it.artifactId },
          )
        }
        .sortedBy { it.id }
    }

    fun eventSink(event: LicensesUiEvent) {
      when (event) {
        is LicensesUiEvent.GoToLink -> navigator.goTo(UrlScreen(event.url))
        LicensesUiEvent.NavigateUp -> navigator.pop()
      }
    }

    return LicensesUiState(
      licenses = licenses,
      eventSink = ::eventSink,
    )
  }
}
