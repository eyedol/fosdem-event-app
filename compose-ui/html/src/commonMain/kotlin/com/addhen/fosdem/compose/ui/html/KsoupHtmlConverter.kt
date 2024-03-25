// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import co.touchlab.kermit.Logger
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser

class KsoupHtmlConverter : HtmlConverter {

  override fun fromHtml(html: String, linkTextColor: Color): AnnotatedString {
    val output = HtmlAnnotatedStringBuilder(linkTextColor)
    val handler = KsoupHtmlHandler
      .Builder()
      .onOpenTag { name, attributes, _ ->
        Logger.d { "tag opening name: $name" }

        when (name) {
          HtmlTag.P.tag -> output.handleParagraphOpenTag()
          HtmlTag.BR.tag -> output.handleLineBreakOpenTag()
          HtmlTag.EM.tag -> output.handleEmOpenTag()
          HtmlTag.UL.tag -> output.handleUlOpenTag()
          HtmlTag.OL.tag -> output.handleOlOpenTag()
          HtmlTag.LI.tag -> output.handleLiOpenTag()
          HtmlTag.H1.tag -> output.handleH1OpenTag()
          HtmlTag.H2.tag -> output.handleH2OpenTag()
          HtmlTag.H3.tag -> output.handleH3OpenTag()
          HtmlTag.H4.tag -> output.handleH4OpenTag()
          HtmlTag.H5.tag -> output.handleH5OpenTag()
          HtmlTag.H6.tag -> output.handleH6OpenTag()
          HtmlTag.STRONG.tag -> output.handleStrongOpenTag()
          HtmlTag.A.tag -> output.handleAOpenTag(attributes["href"].orEmpty())
        }
      }
      .onText { text ->
        Logger.d { "text received: $text" }
        if (text.isBlank()) return@onText

        output.write(text)
      }
      .onCloseTag { name, _ ->
        Logger.d { "tag closing name: $name" }

        when (name) {
          HtmlTag.P.tag -> output.handleParagraphCloseTag()
          HtmlTag.EM.tag -> output.handleEmCloseTag()
          HtmlTag.STRONG.tag -> output.handleStrongCloseTag()
          HtmlTag.UL.tag -> output.handleUlCloseTag()
          HtmlTag.OL.tag -> output.handleOlCloseTag()
          HtmlTag.LI.tag -> output.handleLiCloseTag()
          HtmlTag.H1.tag -> output.handleH1CloseTag()
          HtmlTag.H2.tag -> output.handleH2CloseTag()
          HtmlTag.H3.tag -> output.handleH3CloseTag()
          HtmlTag.H4.tag -> output.handleH4CloseTag()
          HtmlTag.H5.tag -> output.handleH5CloseTag()
          HtmlTag.H6.tag -> output.handleH6CloseTag()
          HtmlTag.A.tag -> output.handleACloseTag()
        }
      }
      .build()

    val parser = KsoupHtmlParser(handler)
    try {
      // Due to AnnotatedString not supporting nested ParagraphStyles,
      // removing any <p> tag between any <li> tag to avoid the app from crashing.
      // See the issue below opened to address it.
      // Issue link: https://issuetracker.google.com/issues/315006870?pli=1
      val strippedParagraphTag = html
        .replace("<li>\n<p>", "<li>")
        .replace("</p>\n</li>", "</li>")
      parser.write(strippedParagraphTag)
    } finally {
      parser.end()
    }

    return output.toAnnotatedString()
  }
}
