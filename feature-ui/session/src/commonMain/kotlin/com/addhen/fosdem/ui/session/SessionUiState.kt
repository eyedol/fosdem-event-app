// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class SessionUiState(
  val eventSink: (SessionUiEvent) -> Unit,
) : CircuitUiState

sealed interface SessionUiEvent : CircuitUiEvent {
  data object OpenItem : SessionUiEvent
}
