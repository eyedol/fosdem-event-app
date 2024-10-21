// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

/**
 * Provides ClickableText with underline for the specified regex.
 * When underlining a string other than a url, please specify the url as well.
 *
 * @param regex Specify a Regex to extract the string for which you want underlined text decoration
 * @param url If the string to be extracted by regex is a string other than a URL, use this one.
 */
@Composable
fun ClickableLinkText(
  style: TextStyle,
  content: String,
  isHtmlContent: Boolean = false,
  onLinkClick: (url: String) -> Unit,
  regex: Regex,
  modifier: Modifier = Modifier,
  overflow: TextOverflow = TextOverflow.Clip,
  maxLines: Int = Int.MAX_VALUE,
  url: String? = null,
  onContentLick: () -> Unit = {},
  onOverflow: (Boolean) -> Unit = {},
) {
  val findResults = findResults(content = content, regex = regex)
  val annotatedString = if (isHtmlContent) {
    content.parseAsHtml()
  } else {
    getAnnotatedString(content = content, findUrlResults = findResults)
  }
  val layoutResult = remember { mutableStateOf<LayoutCoordinates?>(null) }
  val density = LocalDensity.current
  val isLinkClicked = remember { mutableStateOf(false) }

  val isOverflowing by remember {
    derivedStateOf {
      val actualHeight = layoutResult.value?.size?.height?.toFloat() ?: 0f
      val expectedHeight = with(density) { style.fontSize.toPx() * maxLines }
      actualHeight > expectedHeight
    }
  }

  LaunchedEffect(isOverflowing) {
    onOverflow(isOverflowing)
  }

  ClickableTextWithOnlinkClick(
    modifier = modifier.onGloballyPositioned { coordinates ->
      layoutResult.value = coordinates
    },
    text = annotatedString,
    style = style,
    overflow = overflow,
    maxLines = maxLines,
    onClick = { offset ->
      findResults.forEach { matchResult ->
        annotatedString.getStringAnnotations(
          tag = matchResult.value,
          start = offset,
          end = offset,
        ).firstOrNull()?.let {
          isLinkClicked.value = true
          onLinkClick(url ?: matchResult.value)
        }
      }
      if (isLinkClicked.value.not()) {
        isLinkClicked.value = false
        onContentLick()
      }
    },
  )
}

@Composable
private fun ClickableTextWithOnlinkClick(
  text: AnnotatedString,
  modifier: Modifier = Modifier,
  style: TextStyle = TextStyle.Default,
  softWrap: Boolean = true,
  overflow: TextOverflow = TextOverflow.Clip,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  onClick: (Int) -> Unit,
) {
  val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
  val pressIndicator = Modifier.pointerInput(onClick) {
    detectTapGestures { pos ->
      layoutResult.value?.let { layoutResult ->
        onClick(layoutResult.getOffsetForPosition(pos))
      }
    }
  }

  BasicText(
    text = text,
    modifier = modifier.then(pressIndicator),
    style = style,
    softWrap = softWrap,
    overflow = overflow,
    maxLines = maxLines,
    onTextLayout = {
      layoutResult.value = it
      onTextLayout(it)
    },
  )
}

@Composable
private fun findResults(
  content: String,
  regex: Regex,
): Sequence<MatchResult> {
  return remember(content) {
    regex.findAll(content)
  }
}

@Composable
private fun getAnnotatedString(
  content: String,
  findUrlResults: Sequence<MatchResult>,
): AnnotatedString {
  return buildAnnotatedString {
    pushStyle(
      style = SpanStyle(
        color = MaterialTheme.colorScheme.inverseSurface,
      ),
    )
    append(content)
    pop()

    var lastIndex = 0
    findUrlResults.forEach { matchResult ->
      val startIndex = content.indexOf(
        string = matchResult.value,
        startIndex = lastIndex,
      )
      val endIndex = startIndex + matchResult.value.length
      addStyle(
        style = SpanStyle(
          color = MaterialTheme.colorScheme.primary,
          textDecoration = TextDecoration.Underline,
          fontWeight = FontWeight.Bold,
        ),
        start = startIndex,
        end = endIndex,
      )
      addStringAnnotation(
        tag = matchResult.value,
        annotation = matchResult.value,
        start = startIndex,
        end = endIndex,
      )

      lastIndex = endIndex
    }
  }
}
