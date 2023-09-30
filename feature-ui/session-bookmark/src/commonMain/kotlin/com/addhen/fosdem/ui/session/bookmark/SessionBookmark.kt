// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
  val eventSink = uiState.eventSink

  SessionBookmarkScreen(
    uiState = uiState,
    onSessionItemClick = { eventSink(SessionBookmarkUiEvent.GoToSessionDetails(it)) },
    onBackPressClick = { eventSink(SessionBookmarkUiEvent.GoToPreviousScreen) },
    onBookmarkClick = { eventId, isBookmarked ->
      eventSink(SessionBookmarkUiEvent.ToggleSessionBookmark(eventId, isBookmarked))
    },
    onAllFilterChipClick = { eventSink(SessionBookmarkUiEvent.FilterAllBookmarks) },
    onDayFirstChipClick = { eventSink(SessionBookmarkUiEvent.FilterFirstDayBookmarks) },
    onDaySecondChipClick = { eventSink(SessionBookmarkUiEvent.FilterSecondDayBookmarks) },
    modifier = modifier,
  )
}

const val BookmarkScreenTestTag = "BookmarkScreenTestTag"

@Composable
private fun SessionBookmarkScreen(
  uiState: SessionBookmarkUiState,
  onBackPressClick: () -> Unit,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onAllFilterChipClick: () -> Unit,
  onDayFirstChipClick: () -> Unit,
  onDaySecondChipClick: () -> Unit,
  modifier: Modifier,
) {
  val scrollState = rememberLazyListState()
  Scaffold(
    modifier = Modifier
      .testTag(BookmarkScreenTestTag)
      .then(modifier),
    topBar = { SessionBookmarkTopArea(onBackPressClick = onBackPressClick) },
  ) { padding ->
    SessionBookmarkSheet(
      modifier = Modifier,
      scrollState = scrollState,
      onSessionItemClick = onSessionItemClick,
      onBookmarkClick = onBookmarkClick,
      onAllFilterChipClick = onAllFilterChipClick,
      onDayFirstChipClick = onDayFirstChipClick,
      onDaySecondChipClick = onDaySecondChipClick,
      contentPadding = padding,
      uiState = uiState.content,
    )
  }
}
