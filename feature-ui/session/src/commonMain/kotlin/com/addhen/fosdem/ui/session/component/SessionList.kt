// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.theme.trackColors
import com.addhen.fosdem.model.api.Event
import kotlinx.collections.immutable.PersistentMap

const val SessionListTestTag = "SessionList"

private const val HAPTIC_LONG_PRESS = 0

data class SessionListUiState(
  val sessionItemMap: PersistentMap<String, List<Event>>,
  val addSessionFavoriteContentDescription: String,
  val removeSessionFavoriteContentDescription: String,
)

@Composable
fun SessionList(
  uiState: SessionListUiState,
  scrollState: LazyListState,
  onBookmarkClick: (Event, Boolean) -> Unit,
  onSessionItemClick: (Event) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  val density = LocalDensity.current
  LazyColumn(
    modifier = modifier.testTag(SessionListTestTag),
    state = scrollState,
    contentPadding = contentPadding,
  ) {
    itemsIndexed(
      uiState.sessionItemMap.toList(),
      key = { _, (key, _) -> key },
    ) { index, (_, sessionItems) ->
      var rowHeight by remember { mutableIntStateOf(0) }
      var timeTextHeight by remember { mutableIntStateOf(0) }
      val timeTextOffset by remember(density) {
        derivedStateOf {
          // 15.dp is bottom_margin of TimetableListItem
          // 1.dp is height of Divider in TimetableListItem
          val maxOffset = with(density) {
            rowHeight - (timeTextHeight + (15.dp + 1.dp).roundToPx())
          }
          if (index == scrollState.firstVisibleItemIndex) {
            minOf(scrollState.firstVisibleItemScrollOffset, maxOffset).coerceAtLeast(0)
          } else {
            0
          }
        }
      }
      Row(
        modifier = Modifier
          .padding(start = 16.dp)
          .onGloballyPositioned {
            rowHeight = it.size.height
          },
      ) {
        Column(
          modifier = Modifier
            .padding(top = 10.dp)
            .width(58.dp)
            .onGloballyPositioned {
              timeTextHeight = it.size.height
            }
            .offset { IntOffset(0, timeTextOffset) },
          verticalArrangement = Arrangement.Center,
        ) {
          val sessionItem = sessionItems[0]
          Spacer(modifier = Modifier.size(6.dp))
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
              space = 4.dp,
              alignment = Alignment.CenterVertically,
            ),
            modifier = Modifier.semantics(mergeDescendants = true) {
              contentDescription = sessionItem.duration.toString()
            },
          ) {
            Text(
              text = sessionItem.startTime.toString(),
              fontWeight = FontWeight.Medium,
              modifier = Modifier.clearAndSetSemantics {},
            )
            Box(
              modifier = Modifier
                .height(8.dp)
                .width(2.dp)
                .background(MaterialTheme.colorScheme.outlineVariant),
            )
            Text(
              text = sessionItem.duration.toString(),
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.clearAndSetSemantics {},
            )
          }
        }
        Column {
          sessionItems.forEach { sessionItem ->
            val isBookmarked = sessionItem.isBookmarked
            val haptic = LocalHapticFeedback.current
            val addFavoriteCd = uiState.addSessionFavoriteContentDescription
            val removeFavoriteCd = uiState.removeSessionFavoriteContentDescription
            SessionListItem(
              sessionItem = sessionItem,
              addSessionFavoriteContentDescription = addFavoriteCd,
              removeSessionFavoriteContentDescription = removeFavoriteCd,
              modifier = Modifier
                .clickable { onSessionItemClick(sessionItem) }
                .padding(top = 10.dp),
              isBookmarked = isBookmarked,
              onBookmarkClick = { sessionItemLocal, isBookmarkedLocal ->
                if (isBookmarkedLocal) {
                  haptic.performHapticFeedback(
                    HapticFeedbackType(HAPTIC_LONG_PRESS),
                  )
                }
                onBookmarkClick(sessionItemLocal, isBookmarkedLocal)
              },
              chipContent = {
                val trackColor = trackColors(sessionItem.track.name)
                val containerColor = trackColor.backgroundColor
                val labelColor = trackColor.nameColor
                SessionTag(
                  label = sessionItem.track.name,
                  labelColor = labelColor,
                  backgroundColor = containerColor,
                )
                SessionTag(
                  label = sessionItem.room.name,
                  borderColor = MaterialTheme.colorScheme.outline,
                )
              },
            )
          }
        }
      }
    }
  }
}
