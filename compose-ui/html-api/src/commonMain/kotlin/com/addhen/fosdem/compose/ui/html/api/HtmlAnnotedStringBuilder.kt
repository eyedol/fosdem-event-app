package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

class HtmlAnnotedStringBuilder(
  private val output: AnnotatedString.Builder = AnnotatedString.Builder()
) {

  fun handleLineBreakOpenTag() {
    val paragraphStyle1 = ParagraphStyle(
      textIndent = TextIndent(firstLine = 14.sp)
    )
    output.withStyle(paragraphStyle1) { }
  }

  fun write(text: String) {
    output.append(text)
  }

  fun handleLineBreakCloseTag() {
    //output.pop()
  }

  fun toAnnotatedString() = output.toAnnotatedString()
}
