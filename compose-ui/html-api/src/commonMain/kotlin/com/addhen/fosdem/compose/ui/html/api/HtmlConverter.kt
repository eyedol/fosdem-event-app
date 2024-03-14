// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString

interface HtmlConverter {

  fun fromHtml(html: String): AnnotatedString
}

enum class HtmlTag(val tag: String) {
  P("p"),
  BR("br"),
  EM("em"),
  UL("ul"),
  LI("li"),
  STRONG("strong"),
  NONE("none"),
}
