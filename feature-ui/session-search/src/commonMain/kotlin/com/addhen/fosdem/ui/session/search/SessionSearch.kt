// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
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
    onSearchQueryChanged = { eventSink(SessionSearchUiEvent.QuerySearch(it)) },
    onSessionItemClick = { eventSink(SessionSearchUiEvent.GoToSessionDetails(it)) },
    onBookmarkClick = { eventId ->
      eventSink(SessionSearchUiEvent.ToggleSessionBookmark(eventId))
    },
    onDaySelected = { dayTab, isSelected ->
      eventSink(SessionSearchUiEvent.FilterDay(dayTab, isSelected))
    },
    onSessionTrackSelected = { track, isSelected ->
      eventSink(SessionSearchUiEvent.FilterSessionTrack(track, isSelected))
    },
    onSessionRoomSelected = { room, isSelected ->
      eventSink(SessionSearchUiEvent.FilterSessionRoom(room, isSelected))
    },
    modifier = modifier,
  )
}

const val SearchScreenTestTag = "SearchScreenTestTag"

@Composable
private fun SessionSearchScreen(
  uiState: SessionSearchUiState,
  onSearchQueryChanged: (String) -> Unit = {},
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long) -> Unit,
  onDaySelected: (DayTab, Boolean) -> Unit,
  onSessionTrackSelected: (FilterTrack, Boolean) -> Unit,
  onSessionRoomSelected: (FilterRoom, Boolean) -> Unit,
  modifier: Modifier,
) {
  val scrollState = rememberLazyListState()
  Scaffold(
    modifier = modifier.testTag(SearchScreenTestTag),
    topBar = {
      SearchTextFieldAppBar(
        searchQuery = uiState.content.query.queryText,
        onSearchQueryChanged = onSearchQueryChanged,
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
