// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.theme.iconColors
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.EmptySessionItems
import com.addhen.fosdem.ui.session.component.SessionList
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.SessionScreenScrollState
import kotlinx.collections.immutable.PersistentList

const val SessionTabTestTag = "SessionTab"
const val SessionEmptyTestTag = "SessionEmptyTest"

sealed interface SessionSheetUiState {
  val days: PersistentList<DayTab>

  data class Empty(override val days: PersistentList<DayTab>) : SessionSheetUiState

  data class Error(override val days: PersistentList<DayTab>) : SessionSheetUiState

  data class ListSession(
    val sessionListUiStates: Map<DayTab, SessionListUiState>,
    override val days: PersistentList<DayTab>,
  ) : SessionSheetUiState
}

@Composable
internal fun SessionSheet(
  uiState: SessionSheetUiState,
  sessionScreenScrollState: SessionScreenScrollState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  var selectedDay by rememberSaveable(stateSaver = DayTab.Saver) {
    mutableStateOf(DayTab.selectDay(uiState.days))
  }
  val corner by animateIntAsState(
    if (sessionScreenScrollState.isScreenLayoutCalculating ||
      sessionScreenScrollState.isSheetExpandable
    ) {
      40
    } else {
      0
    },
    label = "Session corner state",
  )
  val layoutDirection = LocalLayoutDirection.current
  Surface(
    modifier = modifier.padding(contentPadding.calculateTopPadding()),
    shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
  ) {
    val sessionSheetContentScrollState = rememberSessionSheetContentScrollState()
    Column(
      modifier = Modifier
        .fillMaxSize()
        .nestedScroll(sessionSheetContentScrollState.nestedScrollConnection),
    ) {
      SessionTabRow(
        modifier = Modifier.padding(
          start = contentPadding.calculateStartPadding(layoutDirection),
          end = contentPadding.calculateEndPadding(layoutDirection),
        ),
        tabState = sessionSheetContentScrollState.tabScrollState,
        selectedTabIndex = uiState.days.indexOf(selectedDay),
      ) {
        uiState.days.forEach { day ->
          SessionTab(
            tabTitle = day.title,
            selected = day == selectedDay,
            onClick = {
              selectedDay = day
            },
            modifier = Modifier.testTag(SessionTabTestTag.plus(day.id)),
          )
        }
      }
      when (uiState) {
        is SessionSheetUiState.Empty -> {
          EmptySessionItems(
            message = LocalStrings.current.sessionEmpty,
            graphicContent = {
              Icon(
                imageVector = Icons.Filled.HourglassEmpty,
                contentDescription = null,
                modifier = Modifier
                  .size(96.dp),
                tint = iconColors().background,
              )
            },
            modifier = Modifier.testTag(SessionEmptyTestTag),
          )
        }

        is SessionSheetUiState.ListSession -> {
          SessionList(
            uiState = requireNotNull(uiState.sessionListUiStates[selectedDay]),
            scrollState = rememberLazyListState(),
            onSessionItemClick = onSessionItemClick,
            onBookmarkClick = onBookmarkClick,
            modifier = Modifier
              .fillMaxSize()
              .weight(1f),
            contentPadding = PaddingValues(
              bottom = contentPadding.calculateBottomPadding(),
              start = contentPadding.calculateStartPadding(layoutDirection),
              end = contentPadding.calculateEndPadding(layoutDirection),
            ),
          )
        }

        is SessionSheetUiState.Error -> {

        }
      }
    }
  }
}

@Composable
fun rememberSessionSheetContentScrollState(
  tabScrollState: SessionTabState = rememberSessionTabState(),
): SessionSheetContentScrollState {
  return remember { SessionSheetContentScrollState(tabScrollState) }
}

@Stable
class SessionSheetContentScrollState(
  val tabScrollState: SessionTabState,
) {
  val nestedScrollConnection = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
      return onPreScrollSheetContent(available)
    }

    override fun onPostScroll(
      consumed: Offset,
      available: Offset,
      source: NestedScrollSource,
    ): Offset {
      return onPostScrollSheetContent(available)
    }
  }

  /**
   * @return consumed offset
   */
  private fun onPreScrollSheetContent(availableScrollOffset: Offset): Offset {
    if (availableScrollOffset.y >= 0) return Offset.Zero
    // When scrolled upward
    return if (tabScrollState.isTabExpandable) {
      val prevHeightOffset: Float = tabScrollState.scrollOffset
      tabScrollState.onScroll(availableScrollOffset.y)
      availableScrollOffset.copy(x = 0f, y = tabScrollState.scrollOffset - prevHeightOffset)
    } else {
      Offset.Zero
    }
  }

  /**
   * @return consumed offset
   */
  private fun onPostScrollSheetContent(availableScrollOffset: Offset): Offset {
    if (availableScrollOffset.y < 0f) return Offset.Zero
    return if (tabScrollState.isTabCollapsing && availableScrollOffset.y > 0) {
      // When scrolling downward and overscroll
      val prevHeightOffset = tabScrollState.scrollOffset
      tabScrollState.onScroll(availableScrollOffset.y)
      availableScrollOffset.copy(x = 0f, y = tabScrollState.scrollOffset - prevHeightOffset)
    } else {
      Offset.Zero
    }
  }
}
