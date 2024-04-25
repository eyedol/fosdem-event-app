// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.SnackbarMessageEffect
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheet
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkTopArea
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class SessionBookmarkUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is SessionBookmarkScreen -> {
      ui<SessionBookmarkUiState> { state, modifier ->
        SessionBookmark(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun SessionBookmark(
  uiState: SessionBookmarkUiState,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val eventSink = uiState.eventSink

  SessionBookmarkScreen(
    uiState = uiState,
    snackbarHostState = snackbarHostState,
    onSessionItemClick = { eventSink(SessionBookmarkUiEvent.GoToSessionDetails(it)) },
    onBookmarkClick = { eventId ->
      eventSink(SessionBookmarkUiEvent.ToggleSessionBookmark(eventId))
    },
    onBackClick = { eventSink(SessionBookmarkUiEvent.GoToPreviousScreen) },
    onDayFirstChipClick = { eventSink(SessionBookmarkUiEvent.FilterFirstDayBookmarks) },
    onDaySecondChipClick = { eventSink(SessionBookmarkUiEvent.FilterSecondDayBookmarks) },
    onMessageShown = { eventSink(SessionBookmarkUiEvent.ClearMessage) },
    modifier = modifier,
  )
}

const val BookmarkScreenTestTag = "BookmarkScreenTestTag"

@Composable
private fun SessionBookmarkScreen(
  uiState: SessionBookmarkUiState,
  snackbarHostState: SnackbarHostState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long) -> Unit,
  onBackClick: () -> Unit,
  onDayFirstChipClick: () -> Unit,
  onDaySecondChipClick: () -> Unit,
  onMessageShown: (id: Long) -> Unit,
  modifier: Modifier,
) {
  val scrollState = rememberLazyListState()

  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    message = uiState.message,
    onMessageShown = onMessageShown,
  )

  Scaffold(
    modifier = Modifier
      .testTag(BookmarkScreenTestTag)
      .then(modifier),
    topBar = { SessionBookmarkTopArea(onBackClick) },
  ) { padding ->
    SessionBookmarkSheet(
      modifier = Modifier,
      scrollState = scrollState,
      onSessionItemClick = onSessionItemClick,
      onBookmarkClick = onBookmarkClick,
      onDayFirstChipClick = onDayFirstChipClick,
      onDaySecondChipClick = onDaySecondChipClick,
      contentPadding = padding,
      uiState = uiState.content,
    )
  }
}
