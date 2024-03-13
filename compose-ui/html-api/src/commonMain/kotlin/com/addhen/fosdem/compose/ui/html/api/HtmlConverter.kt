package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString

interface HtmlConverter {

  fun fromHtml(html: String): AnnotatedString
}


enum class HtmlTag(val tag: String) {
  P("p"),
  BR("br"),
}
