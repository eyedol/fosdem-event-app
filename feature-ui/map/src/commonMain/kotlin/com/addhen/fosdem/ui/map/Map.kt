// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.core.api.screens.MapScreen
import com.addhen.fosdem.ui.map.component.MapContentBox
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

const val MapScreenTestTag = "MapScreen"

@Inject
class MapUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is MapScreen -> {
      ui<MapUiState> { state, modifier ->
        Map(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun Map(
  uiState: MapUiState,
  modifier: Modifier = Modifier,
) {
  MapScreen(
    uiState = uiState,
    contentPadding = PaddingValues(),
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapScreen(
  uiState: MapUiState,
  modifier: Modifier,
  contentPadding: PaddingValues,
) {
  val layoutDirection = LocalLayoutDirection.current
  val appStrings = LocalStrings.current
  Scaffold(
    modifier = Modifier
      .testTag(MapScreenTestTag)
      .then(modifier),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = appStrings.mapTitle,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium,
          )
        },
      )
    },
    contentWindowInsets = WindowInsets(
      left = contentPadding.calculateLeftPadding(layoutDirection),
      top = contentPadding.calculateTopPadding(),
      right = contentPadding.calculateRightPadding(layoutDirection),
      bottom = contentPadding.calculateBottomPadding(),
    ),
    content = { padding ->
      MapContentBox(
        imageResource = uiState.imageResource,
        innerPadding = padding,
        modifier = Modifier.fillMaxSize(),
      )
    },
  )
}
