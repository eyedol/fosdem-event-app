// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
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
  LI("li"),
  STRONG("strong"),
  NONE("none"),
}
