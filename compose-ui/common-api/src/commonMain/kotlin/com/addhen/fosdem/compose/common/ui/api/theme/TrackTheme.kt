// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("ktlint:enum-entry-name-case")

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import co.touchlab.kermit.Logger

data class TrackTypeColor(
  val nameColor: Color = md_theme_light_track_type_name,
  val backgroundColor: Color,
)

sealed interface TrackColorScheme {
  val trackTypeColor: TrackTypeColor

  enum class Light(override val trackTypeColor: TrackTypeColor) : TrackColorScheme {
    other(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_other,
      ),
    ),
    keynote(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_keynote,
      ),
    ),
    maintrack(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_main_track,
      ),
    ),
    devroom(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_dev_room,
      ),
    ),
    lightningtalk(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_lightning_talk,
      ),
    ),
    certification(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_certification,
      ),
    ),
    junior(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_certification,
      ),
    ),
    bof(
      TrackTypeColor(
        backgroundColor = md_theme_light_track_type_certification,
      ),
    ),
  }

  enum class Dark(override val trackTypeColor: TrackTypeColor) : TrackColorScheme {
    other(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_other,
      ),
    ),
    keynote(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_keynote,
      ),
    ),
    maintrack(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_main_track,
      ),
    ),
    devroom(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_dev_room,
      ),
    ),
    lightningtalk(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_lightning_talk,
      ),
    ),
    certification(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_certification,
      ),
    ),
    junior(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_certification,
      ),
    ),
    bof(
      TrackTypeColor(
        backgroundColor = md_theme_dark_track_type_certification,
      ),
    ),
  }
}

@Composable
fun trackColors(name: String) = if (isSystemInDarkTheme()) {
  Logger.d { "Track $name" }
  TrackColorScheme.Dark.entries.first { it.name.equals(name, ignoreCase = true) }.trackTypeColor
} else {
  Logger.d { "Track $name" }
  TrackColorScheme.Light.entries.first { it.name.equals(name, ignoreCase = true) }.trackTypeColor
}
