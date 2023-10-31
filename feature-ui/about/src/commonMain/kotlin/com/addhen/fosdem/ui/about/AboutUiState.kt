// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class AboutUiState(
  val versionName: String,
  val eventSink: (AboutUiEvent) -> Unit,
) : CircuitUiState

sealed interface AboutUiEvent : CircuitUiEvent {
  data class GoToAboutItem(val aboutItem: AboutItem) : AboutUiEvent
  data class GoToLink(val url: String) : AboutUiEvent
  data object GoToSession : AboutUiEvent
}

sealed class AboutItem {
  data object License : AboutItem()

  data class PrivacyPolicy(val url: String) : AboutItem()
}
