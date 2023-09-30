// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.ui.session.component.SessionUiType
import com.addhen.fosdem.ui.session.component.Tag
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import kotlinx.collections.immutable.PersistentList

@Immutable
data class SessionUiState(
  val appTitle: String,
  val appLogo: ImageResource,
  val year: String,
  val location: String,
  val tags: PersistentList<Tag>,
  val content: SessionSheetUiState,
  val sessionUiType: SessionUiType,
  val eventSink: (SessionUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionUiEvent : CircuitUiEvent {
  data class GoToSessionDetails(val eventId: Long) : SessionUiEvent

  data class ToggleSessionBookmark(val eventId: Long, val isBookmarked: Boolean) : SessionUiEvent

  data object ToggleSessionUi : SessionUiEvent

  data object SearchSession : SessionUiEvent
}
