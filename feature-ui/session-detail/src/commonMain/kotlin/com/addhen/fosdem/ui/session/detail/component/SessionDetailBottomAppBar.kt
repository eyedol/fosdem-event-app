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
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.compose.common.ui.api.painterResource
import com.addhen.fosdem.model.api.Event

const val SessionDetailBookmarkIconTestTag = "SessionItemDetailBookmarkIcon"

@Composable
fun SessionDetailBottomAppBar(
  event: Event,
  isBookmarked: Boolean,
  addFavorite: String,
  removeFavorite: String,
  bookmarkIcon: ImageResource,
  bookmarkIconReverse: ImageResource,
  icon: ImageResource,
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
                    painter = painterResource(icon),
                    contentDescription = "",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // NOOP ,
                },
                modifier = Modifier.testTag(SessionDetailBookmarkIconTestTag),
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                AnimatedBookmarkIcon(
                  isBookmarked = isBookmarked,
                  eventId = event.id,
                  addFavorite = addFavorite,
                  removeFavorite = removeFavorite,
                  bookmarkIcon = bookmarkIcon,
                  bookmarkIconReverse = bookmarkIconReverse,
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
  bookmarkIcon: ImageResource,
  bookmarkIconReverse: ImageResource,
  eventId: Long,
  onClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
    val animatedBookmarkIconPainter = painterResource(
        if (isBookmarked) {
            bookmarkIconReverse
        } else {
            bookmarkIcon
        },
    )
    Icon(
        painter = animatedBookmarkIconPainter,
        contentDescription = if (isBookmarked) {
            removeFavorite
        } else {
            addFavorite
        },
        modifier = modifier
            .clickable {
              onClick(eventId)
            },
    )
}
