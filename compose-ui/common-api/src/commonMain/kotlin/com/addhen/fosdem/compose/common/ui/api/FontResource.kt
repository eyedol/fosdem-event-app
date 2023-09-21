// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun fontFamilyResource(fontResource: FontResource): FontFamily {
  return fontResource.asFont()
    ?.let { FontFamily(it) }
    ?: FontFamily.Default
}

@Composable
expect fun FontResource.asFont(
  weight: FontWeight = FontWeight.Normal,
  style: FontStyle = FontStyle.Normal,
): Font?

enum class FontResource {
  Signika,
}
