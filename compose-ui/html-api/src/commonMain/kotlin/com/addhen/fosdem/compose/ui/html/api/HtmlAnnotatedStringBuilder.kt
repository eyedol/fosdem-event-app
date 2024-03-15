// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.em

internal class HtmlAnnotatedStringBuilder(
  private val builder: AnnotatedString.Builder = AnnotatedString.Builder(),
) {

  private var tag: HtmlTag = HtmlTag.NONE

  fun handleLineBreakOpenTag() {
    tag = HtmlTag.BR
  }

  fun handleUlOpenTag() {
    tag = HtmlTag.UL
  }

  fun handleLiOpenTag() {
    tag = HtmlTag.LI
    builder.pushStyle(
      ParagraphStyle(
        textAlign = TextAlign.Justify,
        textIndent = TextIndent(firstLine = 1.em, restLine = 1.8.em),
        lineBreak = LineBreak.Paragraph,
      ),
    )
  }

  fun handleEmOpenTag() {
    builder.pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
  }

  fun handleStrongOpenTag() {
    builder.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
  }

  fun handleParagraphOpenTag() {
    tag = HtmlTag.P
    builder.pushStyle(ParagraphStyle(lineBreak = LineBreak.Paragraph))
  }

  fun handleStrongCloseTag() {
    builder.pop()
  }

  fun handleEmCloseTag() {
    builder.pop()
  }

  fun handleParagraphCloseTag() {
    builder.pop()
  }

  fun handleUlCloseTag() {
    builder.append("\r\n")
  }

  fun handleLiCloseTag() {
    builder.pop()
  }

  fun write(text: String) {
    when (tag) {
      HtmlTag.P, HtmlTag.BR -> {
        builder.append(text)
        builder.append("\r\n")
      }

      HtmlTag.UL -> {
        builder.append("\r\n")
      }

      HtmlTag.LI -> {
        builder.append("\u2022 ")
        builder.append(text)
      }

      else -> builder.append(text)
    }
  }

  fun toAnnotatedString() = builder.toAnnotatedString()
}