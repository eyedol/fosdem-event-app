// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.Empty
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.ListBookmark
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState.Loading
import com.addhen.fosdem.ui.session.component.SessionShimmerList
import kotlinx.collections.immutable.PersistentMap

sealed interface SessionBookmarkSheetUiState {
  val isAllSelected: Boolean
  val isDayFirstSelected: Boolean
  val isDaySecondSelected: Boolean

  data class Empty(
    override val isAllSelected: Boolean,
    override val isDayFirstSelected: Boolean,
    override val isDaySecondSelected: Boolean,
  ) : SessionBookmarkSheetUiState

  data class Loading(
    override val isAllSelected: Boolean = false,
    override val isDayFirstSelected: Boolean = false,
    override val isDaySecondSelected: Boolean = false,
  ) : SessionBookmarkSheetUiState

  data class ListBookmark(
    val sessionItemMap: PersistentMap<String, List<Event>>,
    override val isAllSelected: Boolean,
    override val isDayFirstSelected: Boolean,
    override val isDaySecondSelected: Boolean,
  ) : SessionBookmarkSheetUiState
}

@Composable
fun SessionBookmarkSheet(
  uiState: SessionBookmarkSheetUiState,
  scrollState: LazyListState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onAllFilterChipClick: () -> Unit,
  onDayFirstChipClick: () -> Unit,
  onDaySecondChipClick: () -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  val layoutDirection = LocalLayoutDirection.current
  Column(
    modifier = modifier.fillMaxSize(),
  ) {
    BookmarkFilters(
      isAll = uiState.isAllSelected,
      isDayFirst = uiState.isDayFirstSelected,
      isDaySecond = uiState.isDaySecondSelected,
      onAllFilterChipClick = onAllFilterChipClick,
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
        EmptyView(modifier = Modifier.padding(padding))
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

@Composable
private fun EmptyView(
  modifier: Modifier = Modifier,
) {
  val appString = LocalStrings.current

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Box(
      modifier = Modifier
        .size(84.dp)
        .background(
          color = MaterialTheme.colorScheme.secondaryContainer,
          shape = RoundedCornerShape(24.dp),
        ),
      contentAlignment = Alignment.Center,
    ) {
      Icon(
        imageVector = Icons.Default.Bookmark,
        contentDescription = null,
      )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
      text = appString.bookmarkedItemEmpty,
      fontSize = 22.sp,
      fontWeight = FontWeight.Medium,
      lineHeight = 28.sp,
      color = MaterialTheme.colorScheme.onSurface,
      textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.size(8.dp))
    Text(
      text = appString.bookmarkedItemNotFound,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.25.sp,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(modifier = Modifier.height(108.dp))
  }
}
