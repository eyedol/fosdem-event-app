// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class MapUiState(
  val imageResource: ImageResource?,
  val eventSink: (MapUiEvent) -> Unit,
) : CircuitUiState

sealed interface MapUiEvent : CircuitUiEvent {
  data object NavigateUp : MapUiEvent
}
