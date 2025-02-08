// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
fun fontFamilyResource(
  fontResource: AppFontResource,
  weight: FontWeight = FontWeight.Normal,
  style: FontStyle = FontStyle.Normal,
): FontFamily = FontFamily(Font(fontResource.font, weight, style))

enum class AppFontResource(val font: FontResource) {
  SignikaRegular(Res.font.signika_regular),
  SignikaMedium(Res.font.signika_medium),
}
