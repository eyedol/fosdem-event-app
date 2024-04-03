// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import fosdem.compose_ui.common_api.generated.resources.Res
import fosdem.compose_ui.common_api.generated.resources.signika_medium
import fosdem.compose_ui.common_api.generated.resources.signika_regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun fontFamilyResource(
  fontResource: AppFontResource,
  weight: FontWeight = FontWeight.Normal,
  style: FontStyle = FontStyle.Normal
): FontFamily = FontFamily(Font(fontResource.font, weight, style))

@OptIn(ExperimentalResourceApi::class)
enum class AppFontResource(val font: FontResource) {
  SignikaRegular(Res.font.signika_regular),
  SignikaMedium(Res.font.signika_medium),
}
