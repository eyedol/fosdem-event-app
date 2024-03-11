package com.addhen.fosdem.ui.session.detail.html.converter

import androidx.compose.ui.text.AnnotatedString
import co.touchlab.kermit.Logger
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

object HtmlConverter {
  fun toAnnotatedString(html: String): AnnotatedString {
    val output = HtmlWriter(AnnotatedString.Builder())
    val handler = KsoupHtmlHandler
      .Builder()
      .onOpenTag { name, _, _ ->
        "br" -> handleLineBreakOpenTag()
        Logger.d { "tag opening name: $name" }
      }
      .onText { text ->
        if (text.isBlank()) return@onText
        output.append(text)
        Logger.d { "text received: $text" }
      }
      .onCloseTag { name, _ ->
        "br" -> handleLineBreakCloseTag()
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
