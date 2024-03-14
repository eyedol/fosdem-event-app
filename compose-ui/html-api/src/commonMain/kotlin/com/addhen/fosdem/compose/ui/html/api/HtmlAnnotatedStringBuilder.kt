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
import androidx.compose.ui.unit.sp

internal class HtmlAnnotatedStringBuilder(
  private val output: AnnotatedString.Builder = AnnotatedString.Builder(),
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
    output.pushStyle(
      ParagraphStyle(
        textAlign = TextAlign.Justify,
        textIndent = TextIndent(firstLine = 15.sp, restLine = 28.sp),
        lineBreak = LineBreak.Paragraph,
      ),
    )
  }

  fun handleEmOpenTag() {
    output.pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
  }

  fun handleStrongOpenTag() {
    output.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
  }

  fun handleParagraphOpenTag() {
    tag = HtmlTag.P
    output.pushStyle(ParagraphStyle(lineBreak = LineBreak.Paragraph))
  }

  fun handleStrongCloseTag() {
    output.pop()
  }

  fun handleEmCloseTag() {
    output.pop()
  }

  fun handleParagraphCloseTag() {
    output.pop()
  }

  fun handleUlCloseTag() {
    output.append("\r\n")
  }

  fun handleLiCloseTag() {
    output.pop()
  }

  fun write(text: String) {
    when (tag) {
      HtmlTag.P, HtmlTag.BR -> {
        output.append(text)
        output.append("\r\n")
      }

      HtmlTag.UL -> {
        output.append("\r\n")
      }

      HtmlTag.LI -> {
        output.append("\u2022 ")
        output.append(text)
      }

      else -> output.append(text)
    }
  }

  fun toAnnotatedString() = output.toAnnotatedString()
}
