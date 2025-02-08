// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.add_to_bookmarks_title
import com.addhen.fosdem.compose.common.ui.api.remove_from_bookmarks_title
import org.jetbrains.compose.resources.stringResource

const val SessionDetailBookmarkIconTestTag = "SessionItemDetailBookmarkIcon"

@Composable
internal fun SessionBookmarkButton(
  eventId: Long,
  isBookmarked: Boolean,
  onBookmarkClick: (Long) -> Unit,
  expanded: Boolean,
  modifier: Modifier = Modifier,
) {

  ExtendedFloatingActionButton(
    onClick = { onBookmarkClick(eventId) },
    icon = {
      Icon(
        imageVector = when {
          isBookmarked -> Icons.Default.Bookmark
          else -> Icons.Default.BookmarkBorder
        },
        contentDescription = when {
          isBookmarked -> stringResource(Res.string.remove_from_bookmarks_title)
          else -> stringResource(Res.string.add_to_bookmarks_title)
        },
      )
    },
    text = {
      Text(
        when {
          isBookmarked -> stringResource(Res.string.remove_from_bookmarks_title)
          else -> stringResource(Res.string.add_to_bookmarks_title)
        },
      )
    },
    containerColor = when {
      isBookmarked -> FloatingActionButtonDefaults.containerColor
      else -> MaterialTheme.colorScheme.surface
    },
    expanded = expanded,
    modifier = modifier.testTag(SessionDetailBookmarkIconTestTag),
  )
}
