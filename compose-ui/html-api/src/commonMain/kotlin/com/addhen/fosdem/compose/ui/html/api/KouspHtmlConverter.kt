package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString
import co.touchlab.kermit.Logger
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

class KouspHtmlConverter: HtmlConverter {
  override fun fromHtml(html: String): AnnotatedString {
    val output = HtmlAnnotedStringBuilder()
    val handler = KsoupHtmlHandler
      .Builder()
      .onOpenTag { name, _, _ ->
        when (name) {
          HtmlTag.P.tag -> output.handleLineBreakOpenTag()
        }
        Logger.d { "tag opening name: $name" }
      }
      .onText { text ->
        if (text.isBlank()) return@onText
        output.write(text)
        Logger.d { "text received: $text" }
      }
      .onCloseTag { name, _ ->
        when ( name ) {
          HtmlTag.P.tag -> output.handleLineBreakCloseTag()
        }
        Logger.d { "tag closing name: $name" }
      }
      .build()

    val parser = KsoupHtmlParser(handler)
    try {
      parser.write(html)
    } finally {
      parser.end()
    }

    return output.toAnnotatedString()
  }
}
