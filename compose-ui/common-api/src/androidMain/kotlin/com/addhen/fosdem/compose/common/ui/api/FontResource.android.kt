// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun FontResource.asFont(
  weight: FontWeight,
  style: FontStyle,
): Font? = remember(this) {
  val fontResId = when (this) {
    FontResource.Signika -> R.font.signika_medium
  }
  Font(
    resId = fontResId,
    weight = weight,
    style = style,
  )
}
