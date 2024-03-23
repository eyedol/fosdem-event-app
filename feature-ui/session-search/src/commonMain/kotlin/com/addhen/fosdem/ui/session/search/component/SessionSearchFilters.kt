// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

const val SearchFilterTestTag = "SearchFilter"

data class SearchFilterUiState<T>(
  val selectedItems: ImmutableList<T> = emptyList<T>().toImmutableList(),
  val items: ImmutableList<T> = emptyList<T>().toImmutableList(),
  val isSelected: Boolean = false,
  val selectedValues: String = "",
)

@Composable
fun SearchFilter(
  searchFilterDayUiState: SearchFilterUiState<DayTab>,
  searchFilterSessionTrackUiState: SearchFilterUiState<FilterTrack>,
  searchFilterSessionRoomUiState: SearchFilterUiState<FilterRoom>,
  modifier: Modifier = Modifier,
  onDaySelected: (DayTab, Boolean) -> Unit = { _, _ -> },
  onSessionTrackSelected: (FilterTrack, Boolean) -> Unit = { _, _ -> },
  onSessionRoomSelected: (FilterRoom, Boolean) -> Unit = { _, _ -> },
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  LazyRow(
    modifier = modifier.testTag(SearchFilterTestTag),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(horizontal = 16.dp),
  ) {
    item {
      FilterDayChip(
        searchFilterUiState = searchFilterDayUiState,
        onDaySelected = onDaySelected,
      )
    }
    item {
      FilterSessionTrackChip(
        searchFilterUiState = searchFilterSessionTrackUiState,
        onSessionTracksSelected = onSessionTrackSelected,
        onFilterSessionTrackChipClicked = { keyboardController?.hide() },
      )
    }
    item {
      FilterSessionRoomChip(
        searchFilterUiState = searchFilterSessionRoomUiState,
        onSessionRoomSelected = onSessionRoomSelected,
        onFilterSessionRoomChipClicked = { keyboardController?.hide() },
      )
    }
  }
}
