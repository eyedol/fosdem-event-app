// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionUiState(
  val content: SessionSheetUiState,
  val isRefreshing: Boolean,
  val eventSink: (SessionUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionUiEvent

  data class ToggleSessionBookmark(val eventId: Long, val isBookmarked: Boolean) : SessionUiEvent

  data object RefreshSession : SessionUiEvent

  data object BookSession : SessionUiEvent
}
