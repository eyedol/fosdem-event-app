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
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day2
import com.addhen.fosdem.ui.session.component.DayTab
import kotlinx.collections.immutable.toImmutableList

fun Day.toDayTab() = DayTab(
  id = id,
  date = date,
)

@MultiThemePreviews
@Composable
private fun FilterDayChipPreview() {
  val dayTab = day.toDayTab()
  val dayTab2 = day2.toDayTab()
  var uiState by remember {
    mutableStateOf(
      SearchFilterUiState(
        selectedItems = emptyList<DayTab>().toImmutableList(),
        items = listOf(dayTab, dayTab2).toImmutableList(),
      ),
    )
  }
  AppTheme {
    Surface {
      FilterDayChip(
        searchFilterUiState = uiState,
        onDaySelected = { day, isSelected ->
          val selectedDays = uiState.selectedItems.toMutableList()
          val newSelectedDays = selectedDays.apply {
            if (isSelected) {
              add(day)
            } else {
              remove(day)
            }
          }.sortedBy { it.id }
          uiState = uiState.copy(
            selectedItems = newSelectedDays.toImmutableList(),
            isSelected = newSelectedDays.isNotEmpty(),
            selectedValues = newSelectedDays.joinToString { it.date.toString() },
          )
        },
      )
    }
  }
}
