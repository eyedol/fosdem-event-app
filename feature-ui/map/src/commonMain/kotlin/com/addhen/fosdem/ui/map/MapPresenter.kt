// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.core.api.screens.MapScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Inject

@Inject
class MapUiPresenterFactory(
  private val presenterFactory: () -> MapPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is MapScreen -> presenterFactory()
      else -> null
    }
  }
}

@Inject
class MapPresenter : Presenter<MapUiState> {
  @Composable
  override fun present(): MapUiState {
    return MapUiState(imageResource = ImageVectorResource.FosdemCampusMap)
  }
}
