// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.model.api.licenses.License
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class LicensesUiState(
  val licenses: List<LicenseGroup> = emptyList(),
  val eventSink: (LicensesUiEvent) -> Unit,
) : CircuitUiState

sealed interface LicensesUiEvent : CircuitUiEvent {
  data class GoToLink(val url: String) : LicensesUiEvent
  data object NavigateUp : LicensesUiEvent
}

data class LicenseGroup(
  val id: String,
  val artifacts: List<License>
)
