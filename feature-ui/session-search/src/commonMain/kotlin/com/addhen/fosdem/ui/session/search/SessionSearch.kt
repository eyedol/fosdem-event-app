// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchTextFieldAppBar
import com.addhen.fosdem.ui.session.search.component.SessionSearchSheet
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
    onDaySelected = { dayTab, _ -> eventSink(SessionSearchUiEvent.FilterDay(dayTab)) },
    onSessionTrackSelected = { track, _ ->
      eventSink(SessionSearchUiEvent.FilterSessionTrack(track))
    },
    onSessionRoomSelected = { room, _ ->
      eventSink(SessionSearchUiEvent.FilterSessionRoom(room))
    },
    modifier = modifier,
  )
}

const val SearchScreenTestTag = "SearchScreenTestTag"

@Composable
private fun SessionSearchScreen(
  uiState: SessionSearchUiState,
  onSearchQueryChanged: (String) -> Unit = {},
  onBackPressClick: () -> Unit,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onDaySelected: (DayTab, Boolean) -> Unit = { _, _ -> },
  onSessionTrackSelected: (FilterTrack, Boolean) -> Unit = { _, _ -> },
  onSessionRoomSelected: (FilterRoom, Boolean) -> Unit = { _, _ -> },
  modifier: Modifier,
) {
  val scrollState = rememberLazyListState()
  Scaffold(
    modifier = modifier.testTag(SearchScreenTestTag),
    topBar = {
      SearchTextFieldAppBar(
        searchQuery = uiState.content.searchQuery.queryText,
        onSearchQueryChanged = onSearchQueryChanged,
        onBackClick = onBackPressClick,
        testTag = SearchScreenTestTag,
      )
    },
  ) { innerPadding ->
    SessionSearchSheet(
      uiState = uiState.content,
      contentPadding = innerPadding,
      scrollState = scrollState,
      onBookmarkClick = onBookmarkClick,
      onSessionItemClick = onSessionItemClick,
      onSessionTrackSelected = onSessionTrackSelected,
      onSessionRoomSelected = onSessionRoomSelected,
      onDaySelected = onDaySelected,
    )
  }
}
