// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.Track
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event
import kotlinx.collections.immutable.toImmutableList

@MultiThemePreviews
@Composable
fun FilterSessionTrackPreview() {
  var uiState by remember {
    mutableStateOf(
      SearchFilterUiState(
        selectedItems = emptyList<Track>().toImmutableList(),
        items = listOf(day1Event.track, day2Event.track).toImmutableList(),
      ),
    )
  }
  AppTheme {
    Surface {
      FilterSessionTrackChip(
        searchFilterUiState = uiState,
        onSessionTracksSelected = { track, isSelected ->
          val selectedCategories = uiState.selectedItems.toMutableList()
          val newSelectedCategories = selectedCategories.apply {
            if (isSelected) {
              add(track)
            } else {
              remove(track)
            }
          }
          uiState = uiState.copy(
            selectedItems = newSelectedCategories.toImmutableList(),
            isSelected = newSelectedCategories.isNotEmpty(),
            selectedValues = newSelectedCategories.joinToString { it.name },
          )
        },
        onFilterSessionTrackChipClicked = {},
      )
    }
  }
}
