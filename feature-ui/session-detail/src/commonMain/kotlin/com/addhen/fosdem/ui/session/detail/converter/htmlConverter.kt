package com.addhen.fosdem.ui.session.detail.converter

import co.touchlab.kermit.Logger
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

fun convert(html: String): String {
  val output = StringBuilder()
  val handler = KsoupHtmlHandler
    .Builder()
    .onOpenTag { name, _, _ ->
      Logger.d { "tag opening name: $name" }
    }
    .onText { text ->
      if (text.isBlank()) return@onText

      Logger.d { "text received: $text" }
    }
    .onCloseTag { name, _ ->
      Logger.d { "tag closing name: $name" }
    }
    .build()

  val parser = KsoupHtmlParser(handler)
  try {
    parser.write(html)
  } finally {
    parser.end()
  }

  return output.toString()
}
