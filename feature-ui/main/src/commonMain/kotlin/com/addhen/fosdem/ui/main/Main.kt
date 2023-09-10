// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.addhen.fosdem.compose.common.ui.api.Layout
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class MainUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is MainScreen -> {
      ui<MainUiState> { state, modifier ->
        Main(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun Main(
  state: MainUiState,
  modifier: Modifier = Modifier,
) {
  val overlayHost = LocalOverlayHost.current
  val eventSink = state.eventSink

  Main(
    state = state,
    onMessageShown = {},
    onItemClick = { eventSink(MainUiEvent.OpenItem) },
    modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Main(
  state: MainUiState,
  onMessageShown: () -> Unit,
  onItemClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  val dismissSnackbarState = rememberDismissState(
    confirmValueChange = { value ->
      if (value == DismissValue.Default) {
        snackbarHostState.currentSnackbarData?.dismiss()
        true
      } else {
        false
      }
    },
  )

    LaunchedEffect(Unit) {
      snackbarHostState.showSnackbar("No integrations added")
      onMessageShown()
  }

  Scaffold(
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState) { data ->
        SwipeToDismiss(
          state = dismissSnackbarState,
          background = {},
          dismissContent = { Snackbar(snackbarData = data) },
          modifier = Modifier
            .padding(horizontal = Layout.bodyMargin)
            .fillMaxWidth(),
        )
      }
    },
    contentWindowInsets = WindowInsets.systemBars,
    modifier = modifier,
  ) { innerPadding ->
  }
}

internal enum class NavigationType {
  BOTTOM_NAVIGATION,
  RAIL,
  PERMANENT_DRAWER,
  ;

  companion object {
    fun forWindowSizeSize(windowSizeClass: WindowSizeClass): NavigationType = when {
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> RAIL
      else -> PERMANENT_DRAWER
    }
  }
}
