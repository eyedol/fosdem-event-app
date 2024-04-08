// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class MapUiState(
  val imageResource: ImageVectorResource?,
) : CircuitUiState
