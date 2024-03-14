// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

object Html {

  @Composable
  fun fromHtml(
    source: String,
    htmlConverter: HtmlConverter = KouspHtmlConverter(),
  ): AnnotatedString = htmlConverter.fromHtml(source)
}
