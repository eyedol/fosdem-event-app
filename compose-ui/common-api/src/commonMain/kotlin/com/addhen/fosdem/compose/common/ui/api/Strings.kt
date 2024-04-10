// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.lyricist.LanguageTag
import cafe.adriel.lyricist.Lyricist
import com.addhen.fosdem.compose.ui.html.Html
import com.addhen.fosdem.core.api.ConferenceInfo
import com.addhen.fosdem.core.api.FosdemConference
import com.addhen.fosdem.core.api.i18n.AppStrings
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.core.api.i18n.Strings

val LocalStrings: ProvidableCompositionLocal<AppStrings> = compositionLocalOf { EnAppStrings }

val LocalConferenceInfo: ProvidableCompositionLocal<ConferenceInfo> = compositionLocalOf {
  FosdemConference
}

@Composable
fun rememberStrings(
  languageTag: LanguageTag = "en",
): Lyricist<AppStrings> = cafe.adriel.lyricist.rememberStrings(Strings, languageTag)

@Composable
fun ProvideStrings(
  lyricist: Lyricist<AppStrings> = rememberStrings(),
  content: @Composable () -> Unit,
) {
  cafe.adriel.lyricist.ProvideStrings(lyricist, LocalStrings, content)
}

@Composable
fun String.parseAsHtml(
  linkTextColor: Color = MaterialTheme.colorScheme.primary,
) = Html.fromHtml(this, linkTextColor)

