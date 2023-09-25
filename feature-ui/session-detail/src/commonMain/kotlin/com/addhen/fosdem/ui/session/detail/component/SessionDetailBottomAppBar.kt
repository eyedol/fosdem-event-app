// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.AppIcons
import com.addhen.fosdem.compose.common.ui.api.CalendarAddOn
import com.addhen.fosdem.model.api.Event

const val SessionDetailBookmarkIconTestTag = "SessionItemDetailBookmarkIcon"

@Composable
fun SessionDetailBottomAppBar(
  event: Event,
  isBookmarked: Boolean,
  addFavorite: String,
  removeFavorite: String,
  shareTitle: String,
  addToCalendar: String,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onCalendarRegistrationClick: (Event) -> Unit,
  onShareClick: (Event) -> Unit,
  modifier: Modifier = Modifier,
) {
  BottomAppBar(
    modifier = modifier,
    actions = {
      IconButton(onClick = { onShareClick(event) }) {
        Icon(
          imageVector = Icons.Filled.Share,
          contentDescription = shareTitle,
        )
      }
      IconButton(onClick = { onCalendarRegistrationClick(event) }) {
        Icon(
          imageVector = AppIcons.Filled.CalendarAddOn,
          contentDescription = addToCalendar,
        )
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { /* NOOP */ },
        modifier = Modifier.testTag(SessionDetailBookmarkIconTestTag),
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
      ) {
        AnimatedBookmarkIcon(
          isBookmarked = isBookmarked,
          eventId = event.id,
          addFavorite = addFavorite,
          removeFavorite = removeFavorite,
          onClick = onBookmarkClick,
        )
      }
    },
  )
}

@Composable
fun AnimatedBookmarkIcon(
  addFavorite: String,
  removeFavorite: String,
  isBookmarked: Boolean,
  eventId: Long,
  onClick: (Long, Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  val bookmarkVector = if (isBookmarked) {
    Icons.Filled.BookmarkRemove
  } else {
    Icons.Filled.BookmarkAdd
  }
  Icon(
    imageVector = bookmarkVector,
    contentDescription = if (isBookmarked) {
      removeFavorite
    } else {
      addFavorite
    },
    modifier = modifier.clickable { onClick(eventId, !isBookmarked) },
  )
}
