// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalWindowSizeClass
import com.addhen.fosdem.compose.common.ui.api.SnackbarMessageEffect
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.ui.session.component.SessionHeader
import com.addhen.fosdem.ui.session.component.rememberSessionScreenScrollState
import com.addhen.fosdem.ui.session.list.component.SessionSheet
import com.addhen.fosdem.ui.session.list.component.SessionTopArea
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import kotlin.math.roundToInt

const val SessionScreenTestTag = "SessionScreen"

@Inject
class SessionUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is SessionsScreen -> {
      ui<SessionsUiState> { state, modifier ->
        Session(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun Session(
  uiState: SessionsUiState,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val eventSink = uiState.eventSink

  SessionScreen(
    uiState = uiState,
    snackbarHostState = snackbarHostState,
    onSessionItemClick = { eventSink(SessionUiEvent.GoToSessionDetails(it)) },
    onToggleSessionBookmark = { eventId ->
      eventSink(SessionUiEvent.ToggleSessionBookmark(eventId))
    },
    onSearchClick = { eventSink(SessionUiEvent.GoToBookmarkSessions) },
    onSessionRefreshClick = { eventSink(SessionUiEvent.RefreshSession) },
    onPerformSnackbarAction = { eventSink(SessionUiEvent.RefreshSession) },
    onMessageShown = { eventSink(SessionUiEvent.ClearMessage(it)) },
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
  uiState: SessionsUiState,
  snackbarHostState: SnackbarHostState,
  onSessionItemClick: (eventId: Long) -> Unit,
  onToggleSessionBookmark: (eventId: Long) -> Unit,
  onSearchClick: () -> Unit,
  onSessionRefreshClick: () -> Unit,
  onPerformSnackbarAction: () -> Unit,
  onMessageShown: (id: Long) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  val density = LocalDensity.current
  val windowSizeClass = LocalWindowSizeClass.current
  val state = rememberSessionScreenScrollState()
  val layoutDirection = LocalLayoutDirection.current
  val gradientEndRatio = remember(windowSizeClass) {
    windowSizeClass.gradientEndRatio()
  }
  val sessionTopGradient = sessionTopGradient()
  val bottomPaddingPx = with(density) { contentPadding.calculateBottomPadding().toPx() }
  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    message = uiState.message,
    onSnackbarActionPerformed = onPerformSnackbarAction,
    onMessageShown = onMessageShown,
  )

  Scaffold(
    modifier = modifier
      .testTag(SessionScreenTestTag)
      .nestedScroll(state.screenNestedScrollConnection)
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
        isRefreshing = uiState.isRefreshing,
        onRefreshClick = onSessionRefreshClick,
        onBookmarkClicks = onSearchClick,
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
      SessionHeader(
        modifier = Modifier
          .fillMaxWidth()
          .onGloballyPositioned { coordinates ->
            // A magic number to make scrolling the session sheet up to scroll over the
            // header text and image but to not scroll over the top menu items.
            val scrollToTopOffsetMagicNumber = 34.00000f
            val headerHeight = (
              coordinates.size.height.toFloat() - innerPadding
                .calculateTopPadding().value
              ) + scrollToTopOffsetMagicNumber
            state.onHeaderPositioned(headerHeight = headerHeight)
          },
      )
      SessionSheet(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 155.dp)
          .layout { measurable, constraints ->
            val placeable = measurable.measure(
              constraints.copy(
                maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt(),
              ),
            )
            layout(placeable.width, placeable.height) {
              placeable.placeRelative(
                0,
                0 + (state.sheetScrollOffset / 2).roundToInt(),
              )
            }
          },
        onSessionItemClick = onSessionItemClick,
        uiState = uiState.content,
        sessionScreenScrollState = state,
        onBookmarkClick = onToggleSessionBookmark,
        contentPadding = PaddingValues(
          bottom = innerPadding.calculateBottomPadding(),
          start = innerPadding.calculateStartPadding(layoutDirection),
          end = innerPadding.calculateEndPadding(layoutDirection),
        ),
      )
    }
  }
}

private fun WindowSizeClass.gradientEndRatio(): Float = when {
  widthSizeClass == WindowWidthSizeClass.Compact ||
    widthSizeClass == WindowWidthSizeClass.Medium -> 0.5f

  heightSizeClass == WindowHeightSizeClass.Compact -> 0.2f
  else -> 0.2f
}
