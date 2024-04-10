// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString

object Html {

  @Composable
  fun fromHtml(
    source: String,
    linkTextColor: Color,
    htmlConverter: HtmlConverter = KsoupHtmlConverter(),
  ): AnnotatedString = htmlConverter.fromHtml(source, linkTextColor)
}

@Composable
fun String.parseAsHtml(linkTextColor: Color = MaterialTheme.colorScheme.primary) = Html.fromHtml(this, linkTextColor )
