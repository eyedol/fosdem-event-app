// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.AppFontResource
import com.addhen.fosdem.compose.common.ui.api.fontFamilyResource

@Composable
internal fun appTypography(): Typography {
  val signikaRegular = fontFamilyResource(AppFontResource.SignikaRegular)
  val signikaMedium = fontFamilyResource(AppFontResource.SignikaMedium)

  return Typography(
    displayLarge = TextStyle(
      fontSize = 57.sp,
      fontFamily = signikaMedium,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 64.sp,
    ),
    displayMedium = TextStyle(
      fontSize = 45.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 52.sp,
    ),
    displaySmall = TextStyle(
      fontSize = 36.sp,
      fontFamily = signikaMedium,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
      fontSize = 32.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
      fontSize = 28.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
      fontSize = 24.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
      fontSize = 22.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
      fontSize = 16.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
      fontSize = 14.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
      fontSize = 14.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
      fontSize = 12.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
      fontSize = 11.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
      fontSize = 16.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp,
    ),
    bodyMedium = TextStyle(
      fontSize = 14.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
      fontSize = 12.sp,
      fontFamily = signikaRegular,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.4.sp,
    ),
  )
}
