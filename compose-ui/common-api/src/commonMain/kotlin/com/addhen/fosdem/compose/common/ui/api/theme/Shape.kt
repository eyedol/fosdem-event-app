// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
  small = CutCornerShape(
    topStart = 8.dp,
    bottomEnd = 8.dp,
  ),
  medium = CutCornerShape(
    topStart = 8.dp,
    bottomEnd = 8.dp,
  ),
  large = CutCornerShape(
    topStart = 32.dp,
  ),
)
