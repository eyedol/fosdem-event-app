package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak

class HtmlAnnotatedStringBuilder(
  private val output: AnnotatedString.Builder = AnnotatedString.Builder()
) {

  var tag: HtmlTag =  HtmlTag.NONE

  fun handleLineBreakOpenTag() {
    tag = HtmlTag.BR
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

  fun write(text: String) {
    when (tag) {
      HtmlTag.P, HtmlTag.BR -> {
        output.append(text)
        output.append("\r\n")
      }
      else -> output.append(text)
    }
  }

  fun toAnnotatedString() = output.toAnnotatedString()
}
