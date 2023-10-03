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
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.room
import com.addhen.fosdem.model.api.room2
import kotlinx.collections.immutable.toImmutableList

@MultiThemePreviews
@Composable
fun FilterSessionRoomPreview() {
  var uiState by remember {
    mutableStateOf(
      SearchFilterUiState(
        selectedItems = emptyList<Room>().toImmutableList(),
        items = listOf(room, room2).toImmutableList(),
      ),
    )
  }
  AppTheme {
    Surface {
      FilterSessionRoomChip(
        searchFilterUiState = uiState,
        onSessionTypeSelected = { sessionType, isSelected ->
          val selectedSessionTypes = uiState.selectedItems.toMutableList()
          val newSelectedSessionTypes = selectedSessionTypes.apply {
            if (isSelected) {
              add(sessionType)
            } else {
              remove(sessionType)
            }
          }
          uiState = uiState.copy(
            selectedItems = newSelectedSessionTypes.toImmutableList(),
            isSelected = newSelectedSessionTypes.isNotEmpty(),
            selectedValues = newSelectedSessionTypes.joinToString { it.name },
          )
        },
        onFilterSessionTypeChipClicked = {},
      )
    }
  }
}
