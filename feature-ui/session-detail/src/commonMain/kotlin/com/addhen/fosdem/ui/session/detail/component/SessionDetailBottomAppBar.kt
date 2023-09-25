package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
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
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.compose.common.ui.api.painterResource
import com.addhen.fosdem.model.api.Event

const val SessionDetailBookmarkIconTestTag = "SessionItemDetailBookmarkIcon"

sealed interface BookmarkIcon {
  val imageResource: ImageResource

  data class Bookmarked(override val imageResource: ImageResource) : BookmarkIcon
  data class Reversed(override val imageResource: ImageResource) : BookmarkIcon
}

@Composable
fun SessionDetailBottomAppBar(
  event: Event,
  isBookmarked: Boolean,
  addFavorite: String,
  removeFavorite: String,
  bookmarkedIcon: BookmarkIcon.Bookmarked,
  reversedBookmarkIcon: BookmarkIcon.Reversed,
  onBookmarkClick: (Long) -> Unit,
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
          contentDescription = "",
        )
      }
      IconButton(onClick = { onCalendarRegistrationClick(event) }) {
        Icon(
          imageVector = AppIcons.Filled.CalendarAddOn,
          contentDescription = "",
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
          bookmarkedIcon = bookmarkedIcon,
          reversedBookmarkIcon = reversedBookmarkIcon,
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
  bookmarkedIcon: BookmarkIcon.Bookmarked,
  reversedBookmarkIcon: BookmarkIcon.Reversed,
  eventId: Long,
  onClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
  val animatedBookmarkIconPainter = painterResource(
    if (isBookmarked) {
      reversedBookmarkIcon.imageResource
    } else {
      bookmarkedIcon.imageResource
    },
  )
  Icon(
    painter = animatedBookmarkIconPainter,
    contentDescription = if (isBookmarked) {
      removeFavorite
    } else {
      addFavorite
    },
    modifier = modifier.clickable { onClick(eventId) },
  )
}
