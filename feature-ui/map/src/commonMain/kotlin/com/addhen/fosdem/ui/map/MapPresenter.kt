// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.screens.MapScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class MapUiPresenterFactory(
  private val presenterFactory: (Navigator) -> MapPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is MapScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class MapPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<MapUiState> {
  @Composable
  override fun present(): MapUiState {
    fun eventSink(event: MapUiEvent) {
      when (event) {
        MapUiEvent.NavigateUp -> navigator.pop()
      }
    }

    return MapUiState(
      imageResource = null,
      eventSink = ::eventSink,
    )
  }
}
