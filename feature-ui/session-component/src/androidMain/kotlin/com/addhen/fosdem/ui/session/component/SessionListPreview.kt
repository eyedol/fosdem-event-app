// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event1
import kotlinx.collections.immutable.toPersistentMap

@MultiThemePreviews
@Composable
private fun SessionListPreview() {
  val uiState = SessionListUiState(
    sortAndGroupedSessionItems,
  )
  val scrollState = rememberLazyListState()
  AppTheme {
    Surface {
      SessionList(
        uiState = uiState,
        scrollState = scrollState,
        onBookmarkClick = { _, _ -> },
        onSessionItemClick = { _ -> },
        contentPadding = PaddingValues(),
      )
    }
  }
}

val sortAndGroupedSessionItems = listOf(day1Event, day2Event1).groupBy {
  it.startTime.toString() + it.duration.toString()
}.mapValues { entries ->
  entries.value.sortedWith(
    compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
  )
}.toPersistentMap()
