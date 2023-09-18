package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class TrackColorScheme {
  abstract val name: Color
  abstract val typeKeynote: Color
  abstract val typeMainTrack: Color
  abstract val typeDevRoom: Color
  abstract val typeLightningTalk: Color
  abstract val typeCertification: Color
  abstract val typeOther: Color

  data class Light(
    override val typeKeynote: Color = md_theme_light_track_type_keynote,
    override val typeMainTrack: Color = md_theme_light_track_type_main_track,
    override val typeDevRoom: Color = md_theme_light_track_type_dev_room,
    override val typeLightningTalk: Color = md_theme_light_track_type_lightning_talk,
    override val typeCertification: Color = md_theme_light_track_type_certification,
    override val typeOther: Color = md_theme_light_track_type_other,
    override val name: Color = md_theme_light_track_type_name,
  ) : TrackColorScheme()

  data class Dark(
    override val typeKeynote: Color = md_theme_dark_track_type_keynote,
    override val typeMainTrack: Color = md_theme_dark_track_type_main_track,
    override val typeDevRoom: Color = md_theme_dark_track_type_dev_room,
    override val typeLightningTalk: Color = md_theme_dark_track_type_lightning_talk,
    override val typeCertification: Color = md_theme_dark_track_type_certification,
    override val typeOther: Color = md_theme_dark_track_type_other,
    override val name: Color = md_theme_dark_track_type_name,
  ) : TrackColorScheme()
}

@Composable
fun  trackColors() = if (isSystemInDarkTheme()) {
  TrackColorScheme.Dark()
} else {
  TrackColorScheme.Light()
}
