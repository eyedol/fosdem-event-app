// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
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
import com.addhen.fosdem.model.api.Event
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

const val SessionTabTestTag = "SessionTab"

sealed interface SessionSheetUiState {
  val days: List<DayTab>

  data class Empty(override val days: List<DayTab>) : SessionSheetUiState

  data class ListSession(
    val sessionListUiStates: Map<DayTab, SessionListUiState>,
    override val days: List<DayTab>,
  ) : SessionSheetUiState
}

@Immutable
class DayTab(val id: Long, val date: LocalDate) {
  val title: String
    get() = date.dayOfWeek.toString().lowercase()
      .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  companion object {
    private val tzBrussels = TimeZone.of("Europe/Brussels")

    val Saver: Saver<DayTab, *> = listSaver(
      save = { listOf(it.id.toString(), it.date.toString()) },
      restore = {
        DayTab(
          id = it.first().toLong(),
          date = LocalDate.parse(it.last().toString()),
        )
      },
    )

    fun selectDay(days: List<DayTab>): DayTab {
      val reversedEntries = days.sortedByDescending { it.id }
      var selectedDay = reversedEntries.last()
      for (entry in reversedEntries) {
        if (Clock.System.now().toLocalDateTime(tzBrussels).dayOfWeek <= entry.date.dayOfWeek) {
          selectedDay = entry
        }
      }
      return selectedDay
    }
  }
}

@Composable
fun SessionSheet(
  uiState: SessionSheetUiState,
  sessionScreenScrollState: SessionScreenScrollState,
  onSessionItemClick: (Event) -> Unit,
  onFavoriteClick: (Event, Boolean) -> Unit,
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
          // todo
        }

        is SessionSheetUiState.ListSession -> {
          SessionList(
            uiState = requireNotNull(uiState.sessionListUiStates[selectedDay]),
            scrollState = rememberLazyListState(),
            onSessionItemClick = onSessionItemClick,
            onBookmarkClick = onFavoriteClick,
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
