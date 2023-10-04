// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionBookmarkUiState(
  val content: SessionBookmarkSheetUiState,
  val eventSink: (SessionBookmarkUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionBookmarkUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionBookmarkUiEvent

  data class ToggleSessionBookmark(
    val eventId: Long,
    val isBookmarked: Boolean,
  ) : SessionBookmarkUiEvent

  data object FilterAllBookmarks : SessionBookmarkUiEvent

  data object FilterFirstDayBookmarks : SessionBookmarkUiEvent

  data object FilterSecondDayBookmarks : SessionBookmarkUiEvent
}
