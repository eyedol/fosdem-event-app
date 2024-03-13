package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString

object Html {

  fun fromHtml(source: String, htmlConverter: HtmlConverter = KouspHtmlConverter()): AnnotatedString {
    return htmlConverter.fromHtml(source)
  }

}
