// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("ktlint:enum-entry-name-case")

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
sealed interface IconColorScheme {
  val background: Color

  data class Light(
    override val background: Color = md_theme_light_onBackground,
  ) : IconColorScheme

  data class Dark(
    override val background: Color = md_theme_dark_onBackground,
  ) : IconColorScheme
}

@Composable
fun iconColors() = if (isSystemInDarkTheme()) {
  IconColorScheme.Dark()
} else {
  IconColorScheme.Light()
}
