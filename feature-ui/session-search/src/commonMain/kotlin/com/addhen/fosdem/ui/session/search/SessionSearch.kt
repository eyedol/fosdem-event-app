// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.ui.session.search.component.SessionSearchSheet
import com.addhen.fosdem.ui.session.search.component.SessionSearchTopArea
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class SessionSearchUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is SessionSearchScreen -> {
      ui<SessionSearchUiState> { state, modifier ->
        SessionSearch(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun SessionSearch(
  uiState: SessionSearchUiState,
  modifier: Modifier = Modifier,
) {
  val eventSink = uiState.eventSink

  SessionSearchScreen(
    uiState = uiState,
    onSessionItemClick = { eventSink(SessionSearchUiEvent.GoToSessionDetails(it)) },
    onBackPressClick = { eventSink(SessionSearchUiEvent.GoToPreviousScreen) },
    onBookmarkClick = { eventId, isBookmarked ->
      eventSink(SessionSearchUiEvent.ToggleSessionBookmark(eventId, isBookmarked))
    },
    onAllFilterChipClick = { eventSink(SessionSearchUiEvent.FilterAllBookmarks) },
    onDayFirstChipClick = { eventSink(SessionSearchUiEvent.FilterFirstDayBookmarks) },
    onDaySecondChipClick = { eventSink(SessionSearchUiEvent.FilterSecondDayBookmarks) },
    modifier = modifier,
  )
}

const val SearchScreenTestTag = "SearchScreenTestTag"

@Composable
private fun SessionSearchScreen(
  uiState: SessionSearchUiState,
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
      .testTag(SearchScreenTestTag)
      .then(modifier),
    topBar = { SessionSearchTopArea(onBackPressClick = onBackPressClick) },
  ) { padding ->
    SessionSearchSheet(
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
