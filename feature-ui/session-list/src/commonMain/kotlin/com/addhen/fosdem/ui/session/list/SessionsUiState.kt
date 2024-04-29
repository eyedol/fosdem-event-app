// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.ui.session.list.component.SessionsSheetUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionsUiState(
  val content: SessionsSheetUiState,
  val isRefreshing: Boolean,
  val message: UiMessage? = null,
  val eventSink: (SessionUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionUiEvent

  data class ToggleSessionBookmark(val eventId: Long) : SessionUiEvent

  data object RefreshSession : SessionUiEvent

  data object GoToBookmarkSessions : SessionUiEvent

  data object ClearMessage : SessionUiEvent
}
