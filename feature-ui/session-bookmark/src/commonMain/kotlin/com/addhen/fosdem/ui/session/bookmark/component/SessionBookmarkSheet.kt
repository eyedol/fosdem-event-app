// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.Empty
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.ListBookmark
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.Loading
import com.addhen.fosdem.ui.session.component.SessionEmptyListView
import com.addhen.fosdem.ui.session.component.SessionShimmerList
import kotlinx.collections.immutable.PersistentMap

sealed interface SessionBookmarkSheetUiState {
  val isDayFirstSelected: Boolean
  val isDaySecondSelected: Boolean

  data class Empty(
    override val isDayFirstSelected: Boolean,
    override val isDaySecondSelected: Boolean,
  ) : SessionBookmarkSheetUiState

  data class Loading(
    override val isDayFirstSelected: Boolean = false,
    override val isDaySecondSelected: Boolean = false,
  ) : SessionBookmarkSheetUiState

  data class ListBookmark(
    val sessionItemMap: PersistentMap<String, List<Event>>,
    override val isDayFirstSelected: Boolean,
    override val isDaySecondSelected: Boolean,
  ) : SessionBookmarkSheetUiState
}

@Composable
fun SessionBookmarkSheet(
  uiState: SessionBookmarkSheetUiState,
  scrollState: LazyListState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long) -> Unit,
  onDayFirstChipClick: () -> Unit,
  onDaySecondChipClick: () -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  val layoutDirection = LocalLayoutDirection.current
  val appString = LocalStrings.current
  Column(
    modifier = modifier.fillMaxSize(),
  ) {
    BookmarkFilters(
      isDayFirst = uiState.isDayFirstSelected,
      isDaySecond = uiState.isDaySecondSelected,
      onDayFirstChipClick = onDayFirstChipClick,
      onDaySecondChipClick = onDaySecondChipClick,
      modifier = Modifier.padding(
        start = contentPadding.calculateLeftPadding(layoutDirection),
        end = contentPadding.calculateRightPadding(layoutDirection),
        top = contentPadding.calculateTopPadding(),
      ),
    )
    val padding = PaddingValues(
      start = contentPadding.calculateLeftPadding(layoutDirection),
      end = contentPadding.calculateRightPadding(layoutDirection),
      bottom = contentPadding.calculateBottomPadding(),
    )
    when (uiState) {
      is Empty -> {
        SessionEmptyListView(
          title = appString.bookmarkedItemEmpty,
          description = appString.bookmarkedItemNotFound,
          modifier = Modifier.padding(padding),
        ) {
          Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            modifier = Modifier.size(96.dp),
          )
        }
      }

      is Loading -> {
        SessionShimmerList(
          modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
          contentPadding = PaddingValues(
            bottom = contentPadding.calculateBottomPadding(),
            start = contentPadding.calculateStartPadding(layoutDirection),
            end = contentPadding.calculateEndPadding(layoutDirection),
          ),
        )
      }

      is ListBookmark -> {
        BookmarkList(
          scrollState = scrollState,
          sessionItemMap = uiState.sessionItemMap,
          onSessionItemClick = onSessionItemClick,
          onBookmarkIconClick = onBookmarkClick,
          contentPadding = padding,
        )
      }
    }
  }
}
