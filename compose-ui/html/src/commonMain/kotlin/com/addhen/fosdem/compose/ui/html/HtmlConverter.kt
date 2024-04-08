// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString

interface HtmlConverter {

  fun fromHtml(html: String, linkTextColor: Color): AnnotatedString
}

enum class HtmlTag(val tag: String) {
  A("a"),
  P("p"),
  BR("br"),
  EM("em"),
  UL("ul"),
  OL("ol"),
  LI("li"),
  H1("h1"),
  H2("h2"),
  H3("h3"),
  H4("h4"),
  H5("h5"),
  H6("h6"),
  STRONG("strong"),
  NONE("none"),
}
