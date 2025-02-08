// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.addhen.fosdem.compose.ui.html.Html
import com.addhen.fosdem.core.api.ConferenceInfo
import com.addhen.fosdem.core.api.FosdemConference

val LocalConferenceInfo: ProvidableCompositionLocal<ConferenceInfo> = compositionLocalOf {
  FosdemConference
}

@Composable
fun String.parseAsHtml(
  linkTextColor: Color = MaterialTheme.colorScheme.primary,
) = Html.fromHtml(this, linkTextColor)
