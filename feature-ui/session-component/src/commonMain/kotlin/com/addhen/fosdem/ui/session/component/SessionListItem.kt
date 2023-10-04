// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.theme.iconColors
import com.addhen.fosdem.compose.common.ui.api.theme.md_theme_light_outline
import com.addhen.fosdem.model.api.Event
import kotlin.math.max

const val SessionListItemTestTag = "SessionListItem"
const val SessionListItemBookmarkIconTestTag = "SessionListItemBookmarkIconTestTag"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionListItem(
  sessionItem: Event,
  isBookmarked: Boolean,
  onBookmarkClick: (Event, Boolean) -> Unit,
  chipContent: @Composable RowScope.() -> Unit,
  highlightQuery: SearchQuery,
  modifier: Modifier = Modifier,
) {
  val appStrings = LocalStrings.current
  Column(
    modifier.testTag(SessionListItemTestTag),
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      FlowRow(
        modifier = Modifier.weight(1F).padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        chipContent()
      }
      IconToggleButton(
        modifier = Modifier.testTag(SessionListItemBookmarkIconTestTag),
        checked = isBookmarked,
        onCheckedChange = { onBookmarkClick(sessionItem, isBookmarked.not()) },
        colors = IconButtonDefaults.iconToggleButtonColors(
          checkedContentColor = MaterialTheme.colorScheme.onSurface,
        ),
      ) {
        Icon(
          imageVector = if (isBookmarked) {
            Icons.Filled.Bookmark
          } else {
            Icons.Outlined.BookmarkBorder
          },
          contentDescription = if (isBookmarked) {
            appStrings.addToBookmarksTitle
          } else {
            appStrings.removeFromBookmarksTitle
          },
          modifier = Modifier.padding(top = 4.dp),
        )
      }
    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(
      text = buildAnnotatedString {
        sessionItem.title.let { title ->
          val highlightRange = with(highlightQuery) {
            title.getMatchIndexRange()
          }
          append(title.take(highlightRange.first))
          withStyle(
            SpanStyle(
              background = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
              textDecoration = TextDecoration.Underline,
            ),
          ) {
            append(title.substring(highlightRange))
          }
          append(title.takeLast(max((title.lastIndex - highlightRange.last), 0)))
        }
      },
      fontSize = 22.sp,
      lineHeight = 28.sp,
    )

    Spacer(modifier = Modifier.size(8.dp))

    Spacer(modifier = Modifier.size(12.dp))
    Column {
      sessionItem.speakers.forEachIndexed { index, speaker ->
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Image(
            painter = rememberVectorPainter(image = Icons.Default.Person),
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColors().background),
            modifier = Modifier
              .size(32.dp)
              .clip(RoundedCornerShape(12.dp))
              .border(
                BorderStroke(1.dp, md_theme_light_outline),
                RoundedCornerShape(12.dp),
              ),
          )
          Spacer(modifier = Modifier.size(10.dp))
          Text(
            text = speaker.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
          )
        }
        if (sessionItem.speakers.lastIndex != index) {
          Spacer(modifier = Modifier.size(8.dp))
        }
      }
    }
    Spacer(modifier = Modifier.size(15.dp))
    Divider()
  }
}
