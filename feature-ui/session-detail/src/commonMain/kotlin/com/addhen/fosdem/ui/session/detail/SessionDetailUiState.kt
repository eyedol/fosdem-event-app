// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionDetailUiState(
  val eventSink: (SessionDetailUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionDetailUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionDetailUiEvent

  data class ToggleSessionBookmark(val eventId: Long, val isBookmarked: Boolean) : SessionDetailUiEvent

  data object ToggleSessionUi : SessionDetailUiEvent

  data object SearchSession : SessionDetailUiEvent
}
