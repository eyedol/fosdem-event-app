// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2
import com.addhen.fosdem.model.api.day2Event
import com.addhen.fosdem.ui.session.detail.component.DayTab
import com.addhen.fosdem.ui.session.detail.component.SessionListUiState
import com.addhen.fosdem.ui.session.detail.component.SessionScreenScrollState
import com.addhen.fosdem.ui.session.detail.component.SessionSheet
import com.addhen.fosdem.ui.session.detail.component.SessionSheetUiState
import kotlinx.collections.immutable.toPersistentMap

@MultiThemePreviews
@Composable
fun SessionSheetPreview() {
  val sessionListUiState = SessionListUiState(
    sortAndGroupedEventsItems,
    addSessionFavoriteContentDescription = "Add session favorite",
    removeSessionFavoriteContentDescription = "Remove session favorite",
  )

  val sessionListUiState2 = SessionListUiState(
    sortAndGroupedEventsItems2,
    addSessionFavoriteContentDescription = "Add session favorite",
    removeSessionFavoriteContentDescription = "Remove session favorite",
  )

  val dayTab = day.toDayTab()
  val dayTab2 = day2.toDayTab()

  val uiState = SessionSheetUiState.ListSession(
    days = listOf(dayTab, dayTab2),
    sessionListUiStates = mapOf(
      dayTab to sessionListUiState,
      dayTab2 to sessionListUiState2,
    ),
  )

  AppTheme {
    Surface {
      SessionSheet(
        uiState = uiState,
        sessionScreenScrollState = SessionScreenScrollState(),
        onSessionItemClick = { _ -> },
        onBookmarkClick = { _, _ -> },
        contentPadding = PaddingValues(),
      )
    }
  }
}

fun Day.toDayTab() = DayTab(
  id = id,
  date = date,
)

val sortAndGroupedEventsItems = listOf(day1Event, day2Event).groupBy {
  it.startTime.toString() + it.duration.toString()
}.mapValues { entries ->
  entries.value.sortedWith(
    compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
  )
}.toPersistentMap()

val sortAndGroupedEventsItems2 = listOf(day2Event).groupBy {
  it.startTime.toString() + it.duration.toString()
}.mapValues { entries ->
  entries.value.sortedWith(
    compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
  )
}.toPersistentMap()
