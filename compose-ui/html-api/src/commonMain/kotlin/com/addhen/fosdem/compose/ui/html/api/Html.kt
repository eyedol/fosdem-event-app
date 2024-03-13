package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

object Html {

  @Composable
  fun fromHtml(source: String, htmlConverter: HtmlConverter = KouspHtmlConverter()): AnnotatedString {
    return htmlConverter.fromHtml(source)
  }

}
