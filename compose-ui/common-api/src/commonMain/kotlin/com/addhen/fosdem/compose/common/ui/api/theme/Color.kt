// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// From: https://m3.material.io/theme-builder#/custom
// Light Scheme
val md_theme_light_primary = Color(0xFFA300B7)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFD6FC)
val md_theme_light_onPrimaryContainer = Color(0xFF36003D)
val md_theme_light_secondary = Color(0xFF914278)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFFFD8EC)
val md_theme_light_onSecondaryContainer = Color(0xFF3B002E)
val md_theme_light_tertiary = Color(0xFF0D60A9)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFD4E3FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001C39)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1E1A1D)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1E1A1D)
val md_theme_light_surfaceVariant = Color(0xFFEDDFE8)
val md_theme_light_onSurfaceVariant = Color(0xFF4D444C)
val md_theme_light_outline = Color(0xFF7F747C)
val md_theme_light_inverseOnSurface = Color(0xFFF7EEF2)
val md_theme_light_inverseSurface = Color(0xFF332F32)
val md_theme_light_inversePrimary = Color(0xFFFDAAFF)
val md_theme_light_surfaceTint = Color(0xFFA300B7)
val md_theme_light_outlineVariant = Color(0xFFD0C3CC)
val md_theme_light_scrim = Color(0xFF000000)

// Dark Scheme
val md_theme_dark_primary = Color(0xFFFDAAFF)
val md_theme_dark_onPrimary = Color(0xFF580063)
val md_theme_dark_primaryContainer = Color(0xFF7D008C)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFD6FC)
val md_theme_dark_secondary = Color(0xFFFFAEDF)
val md_theme_dark_onSecondary = Color(0xFF591147)
val md_theme_dark_secondaryContainer = Color(0xFF752A5F)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFD8EC)
val md_theme_dark_tertiary = Color(0xFFA4C9FF)
val md_theme_dark_onTertiary = Color(0xFF00315D)
val md_theme_dark_tertiaryContainer = Color(0xFF004883)
val md_theme_dark_onTertiaryContainer = Color(0xFFD4E3FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1E1A1D)
val md_theme_dark_onBackground = Color(0xFFE9E0E4)
val md_theme_dark_surface = Color(0xFF1E1A1D)
val md_theme_dark_onSurface = Color(0xFFE9E0E4)
val md_theme_dark_surfaceVariant = Color(0xFF4D444C)
val md_theme_dark_onSurfaceVariant = Color(0xFFD0C3CC)
val md_theme_dark_outline = Color(0xFF998D96)
val md_theme_dark_inverseOnSurface = Color(0xFF1E1A1D)
val md_theme_dark_inverseSurface = Color(0xFFE9E0E4)
val md_theme_dark_inversePrimary = Color(0xFFA300B7)
val md_theme_dark_surfaceTint = Color(0xFFFDAAFF)
val md_theme_dark_outlineVariant = Color(0xFF4D444C)
val md_theme_dark_scrim = Color(0xFF000000)

// Track colors
val md_theme_light_track_type_keynote = Color(0xFF0A57A4)
val md_theme_light_track_type_main_track = Color(0xFF5E5504)
val md_theme_light_track_type_dev_room = Color(0xFF552E21)
val md_theme_light_track_type_lightning_talk = Color(0xFF026066)
val md_theme_light_track_type_certification = Color(0xFF494E5A)
val md_theme_light_track_type_other = Color(0xFF494E5A)
val md_theme_light_track_type_name = Color(0xFFFFFFFF)

val md_theme_dark_track_type_keynote = Color(0xFFB2D1FF)
val md_theme_dark_track_type_main_track = Color(0xFFF0DF63)
val md_theme_dark_track_type_dev_room = Color(0xFFE7BB92)
val md_theme_dark_track_type_lightning_talk = Color(0xFFB4DDDD)
val md_theme_dark_track_type_certification = Color(0xFFB8B9BD)
val md_theme_dark_track_type_other = Color(0xFFB8B9BD)

val md_theme_dark_tag_color_main = Color(0xFFFFFFFF)
val md_theme_dark_tag_color_alt = Color(0xFF707070)

val md_theme_light_tag_color_main = Color(0xFF000000)
val md_theme_light_tag_color_alt = Color(0xFFA6A6A6)

val fosdem_pink = Color(0xFFA518BA)

val AppLightColors = lightColorScheme(
  primary = md_theme_light_primary,
  onPrimary = md_theme_light_onPrimary,
  primaryContainer = md_theme_light_primaryContainer,
  onPrimaryContainer = md_theme_light_onPrimaryContainer,
  secondary = md_theme_light_secondary,
  onSecondary = md_theme_light_onSecondary,
  secondaryContainer = md_theme_light_secondaryContainer,
  onSecondaryContainer = md_theme_light_onSecondaryContainer,
  tertiary = md_theme_light_tertiary,
  onTertiary = md_theme_light_onTertiary,
  tertiaryContainer = md_theme_light_tertiaryContainer,
  onTertiaryContainer = md_theme_light_onTertiaryContainer,
  error = md_theme_light_error,
  onError = md_theme_light_onError,
  errorContainer = md_theme_light_errorContainer,
  onErrorContainer = md_theme_light_onErrorContainer,
  outline = md_theme_light_outline,
  background = md_theme_light_background,
  onBackground = md_theme_light_onBackground,
  surface = md_theme_light_surface,
  onSurface = md_theme_light_onSurface,
  surfaceVariant = md_theme_light_surfaceVariant,
  onSurfaceVariant = md_theme_light_onSurfaceVariant,
  inverseSurface = md_theme_light_inverseSurface,
  inverseOnSurface = md_theme_light_inverseOnSurface,
  inversePrimary = md_theme_light_inversePrimary,
  surfaceTint = md_theme_light_surfaceTint,
  outlineVariant = md_theme_light_outlineVariant,
  scrim = md_theme_light_scrim,
)

val AppDarkColors = darkColorScheme(
  primary = md_theme_dark_primary,
  onPrimary = md_theme_dark_onPrimary,
  primaryContainer = md_theme_dark_primaryContainer,
  onPrimaryContainer = md_theme_dark_onPrimaryContainer,
  secondary = md_theme_dark_secondary,
  onSecondary = md_theme_dark_onSecondary,
  secondaryContainer = md_theme_dark_secondaryContainer,
  onSecondaryContainer = md_theme_dark_onSecondaryContainer,
  tertiary = md_theme_dark_tertiary,
  onTertiary = md_theme_dark_onTertiary,
  tertiaryContainer = md_theme_dark_tertiaryContainer,
  onTertiaryContainer = md_theme_dark_onTertiaryContainer,
  error = md_theme_dark_error,
  onError = md_theme_dark_onError,
  errorContainer = md_theme_dark_errorContainer,
  onErrorContainer = md_theme_dark_onErrorContainer,
  outline = md_theme_dark_outline,
  background = md_theme_dark_background,
  onBackground = md_theme_dark_onBackground,
  surface = md_theme_dark_surface,
  onSurface = md_theme_dark_onSurface,
  surfaceVariant = md_theme_dark_surfaceVariant,
  onSurfaceVariant = md_theme_dark_onSurfaceVariant,
  inverseSurface = md_theme_dark_inverseSurface,
  inverseOnSurface = md_theme_dark_inverseOnSurface,
  inversePrimary = md_theme_dark_inversePrimary,
  surfaceTint = md_theme_dark_surfaceTint,
  outlineVariant = md_theme_dark_outlineVariant,
  scrim = md_theme_dark_scrim,
)
