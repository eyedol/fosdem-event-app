package com.addhen.fosdem.compose.ui.html.api

import androidx.compose.ui.text.AnnotatedString
import co.touchlab.kermit.Logger
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

class KouspHtmlConverter: HtmlConverter {

  override fun fromHtml(html: String): AnnotatedString {
    val output = HtmlAnnotatedStringBuilder()
    val handler = KsoupHtmlHandler
      .Builder()
      .onOpenTag { name, _, _ ->
        Logger.d { "tag opening name: $name" }

        when (name) {
          HtmlTag.P.tag -> output.handleParagraphOpenTag()
          HtmlTag.BR.tag -> output.handleLineBreakOpenTag()
          HtmlTag.EM.tag -> output.handleEmOpenTag()
          HtmlTag.STRONG.tag -> output.handleStrongOpenTag()
        }
      }
      .onText { text ->
        Logger.d { "text received: $text" }
        if (text.isBlank()) return@onText

        output.write(text)
      }
      .onCloseTag { name, _ ->
        Logger.d { "tag closing name: $name" }

        when ( name ) {
          HtmlTag.P.tag -> output.handleParagraphCloseTag()
          HtmlTag.EM.tag -> output.handleEmCloseTag()
          HtmlTag.STRONG.tag -> output.handleStrongCloseTag()
        }
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
