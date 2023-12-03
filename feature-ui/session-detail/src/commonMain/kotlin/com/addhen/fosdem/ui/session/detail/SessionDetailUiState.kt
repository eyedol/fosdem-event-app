// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.model.api.Event
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionDetailUiState(
  val sessionDetailScreenUiState: ScreenDetailScreenUiState,
  val eventSink: (SessionDetailUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionDetailUiEvent : CircuitUiEvent {
  data object GoToSession : SessionDetailUiEvent

  data class ToggleSessionBookmark(
    val eventId: Long,
  ) : SessionDetailUiEvent

  data class RegisterSessionToCalendar(val event: Event) : SessionDetailUiEvent

  data class ShareSession(val event: Event) : SessionDetailUiEvent

  data class ShowLink(val url: String) : SessionDetailUiEvent
}
