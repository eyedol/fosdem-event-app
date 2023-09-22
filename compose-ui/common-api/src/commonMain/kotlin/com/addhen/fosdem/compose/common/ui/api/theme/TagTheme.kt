// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed interface TagColorScheme {
  val tagColorMain: Color
  val tagColorAlt: Color

  data class Light(
    override val tagColorMain: Color = md_theme_light_tag_color_main,
    override val tagColorAlt: Color = md_theme_light_tag_color_alt,
  ) : TagColorScheme

  data class Dark(
    override val tagColorMain: Color = md_theme_dark_tag_color_main,
    override val tagColorAlt: Color = md_theme_dark_tag_color_alt,
  ) : TagColorScheme
}

@Composable
fun tagColors() = if (isSystemInDarkTheme()) {
  TagColorScheme.Dark()
} else {
  TagColorScheme.Light()
}
