// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.ui.session.component.DayTab

const val FilterDayChipTestTag = "FilterDayChip"

@Composable
fun FilterDayChip(
  searchFilterUiState: SearchFilterUiState<DayTab>,
  onDaySelected: (DayTab, Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  DropdownFilterChip(
    searchFilterUiState = searchFilterUiState,
    onSelected = onDaySelected,
    filterChipLabelDefaultText = LocalStrings.current.dayTitle,
    dropdownMenuItemText = { dayTab -> dayTab.title },
    modifier = modifier.testTag(FilterDayChipTestTag),
  )
}
