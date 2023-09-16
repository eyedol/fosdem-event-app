// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.LocalWindowSizeClass
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.ui.session.component.SessionTopArea
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

const val SessionScreenTestTag = "SessionScreen"

@Inject
class SessionUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is SessionScreen -> {
      ui<SessionUiState> { state, modifier ->
        Session(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun Session(
  uiState: SessionUiState,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  SessionScreen(
    uiState = uiState,
    snackbarHostState = snackbarHostState,
    onSessionItemClick = {},
    onBookmarkClick = {},
    onSearchClick = {},
    onSessionUiChangeClick = {},
    contentPadding = PaddingValues(),
    modifier = modifier,
  )
}

private val sessionTopBackgroundLight = Color(0xFFA518BA)
private val sessionTopBackgroundDark = Color(0xFF4A0A54)

@Composable
@ReadOnlyComposable
private fun sessionTopBackground() = if (!isSystemInDarkTheme()) {
  sessionTopBackgroundLight
} else {
  sessionTopBackgroundDark
}

private val sessionTopGradientLight = Color(0xFFE486F2)
private val sessionTopGradientDark = Color(0xFF9F6FA6)

@Composable
@ReadOnlyComposable
private fun sessionTopGradient() = if (!isSystemInDarkTheme()) {
  sessionTopGradientLight
} else {
  sessionTopGradientDark
}

@Composable
private fun SessionScreen(
  uiState: SessionUiState,
  snackbarHostState: SnackbarHostState,
  onSessionItemClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  onSearchClick: () -> Unit,
  onSessionUiChangeClick: () -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  val density = LocalDensity.current
  val strings = LocalStrings.current
  val windowSizeClass = LocalWindowSizeClass.current
  // val state = remembersessionScreenScrollState()
  val layoutDirection = LocalLayoutDirection.current
  val gradientEndRatio = remember(windowSizeClass) {
    windowSizeClass.gradientEndRatio()
  }
  val sessionTopGradient = sessionTopGradient()
  val bottomPaddingPx = with(density) { contentPadding.calculateBottomPadding().toPx() }
  Scaffold(
    modifier = modifier
      .testTag(SessionScreenTestTag)
      // .nestedScroll(state.screenNestedScrollConnection)
      .background(sessionTopBackground())
      .drawWithCache {
        onDrawBehind {
          drawRect(
            brush = Brush.verticalGradient(
              0f to sessionTopGradient,
              gradientEndRatio to Color.Transparent,
            ),
            size = Size(
              size.width,
              size.height - bottomPaddingPx,
            ),
          )
        }
      },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    topBar = {
      SessionTopArea(
        sessionUiType = uiState.sessionUiType,
        onSessionUiChangeClick = onSessionUiChangeClick,
        onSearchClick = onSearchClick,
        appStrings = strings,
        titleIcon = {},
      )
    },
    contentWindowInsets = WindowInsets(
      left = contentPadding.calculateLeftPadding(layoutDirection),
      top = contentPadding.calculateTopPadding(),
      right = contentPadding.calculateRightPadding(layoutDirection),
      bottom = contentPadding.calculateBottomPadding(),
    ),
    containerColor = Color.Transparent,
  ) { innerPadding ->
    Box(
      modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
    ) {
    }
  }
}

private fun WindowSizeClass.gradientEndRatio(): Float = when {
  widthSizeClass == WindowWidthSizeClass.Compact ||
    widthSizeClass == WindowWidthSizeClass.Medium -> 0.5f
  heightSizeClass == WindowHeightSizeClass.Compact -> 0.2f
  else -> 0.2f
}
