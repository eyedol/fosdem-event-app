// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class MainUiState(
  val eventSink: (MainUiEvent) -> Unit,
) : CircuitUiState

sealed interface MainUiEvent : CircuitUiEvent {
  data object OpenItem : MainUiEvent
}
