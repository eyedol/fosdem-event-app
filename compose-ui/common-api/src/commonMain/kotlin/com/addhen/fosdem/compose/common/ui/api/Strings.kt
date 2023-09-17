// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.lyricist.LanguageTag
import cafe.adriel.lyricist.Lyricist
import com.addhen.fosdem.core.api.i18n.AppStrings
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.core.api.i18n.Strings

val LocalStrings: ProvidableCompositionLocal<AppStrings> = compositionLocalOf { EnAppStrings }

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