// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.ui.session.search.component.SessionSearchSheetUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionSearchUiState(
  val content: SessionSearchSheetUiState,
  val eventSink: (SessionSearchUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionSearchUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionSearchUiEvent

  data class ToggleSessionBookmark(
    val eventId: Long,
    val isBookmarked: Boolean,
  ) : SessionSearchUiEvent

  data object FilterAllBookmarks : SessionSearchUiEvent

  data object FilterFirstDayBookmarks : SessionSearchUiEvent

  data object FilterSecondDayBookmarks : SessionSearchUiEvent

  data object GoToPreviousScreen : SessionSearchUiEvent
}
