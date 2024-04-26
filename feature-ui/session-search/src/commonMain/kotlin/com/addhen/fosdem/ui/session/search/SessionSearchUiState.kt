// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionSearchUiState(
  val content: SearchUiState,
  val eventSink: (SessionSearchUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionSearchUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionSearchUiEvent

  data class ToggleSessionBookmark(
    val eventId: Long,
  ) : SessionSearchUiEvent

  data class FilterDay(
    val dayTab: DayTab,
    val isSelected: Boolean,
  ) : SessionSearchUiEvent

  data class FilterSessionRoom(
    val room: FilterRoom,
    val isSelected: Boolean,
  ) : SessionSearchUiEvent

  data class FilterSessionTrack(
    val track: FilterTrack,
    val isSelected: Boolean,
  ) : SessionSearchUiEvent

  data class QuerySearch(val query: String) : SessionSearchUiEvent
}
