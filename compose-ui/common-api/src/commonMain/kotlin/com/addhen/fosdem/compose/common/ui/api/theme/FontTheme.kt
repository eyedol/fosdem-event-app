// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed interface LogoColorScheme {
  val background: Color

  data class Light(
    override val background: Color = md_them_light_logo_color,
  ) : LogoColorScheme

  data class Dark(
    override val background: Color = md_them_dark_logo_color,
  ) : LogoColorScheme
}

@Composable
fun logoColors() = if (isSystemInDarkTheme()) {
  LogoColorScheme.Dark()
} else {
  LogoColorScheme.Light()
}
