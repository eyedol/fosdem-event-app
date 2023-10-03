// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.ui.session.component.FilterRoom

const val FilterSessionRoomChipTestTag = "FilterSessionTypeChip"

@Composable
fun FilterSessionRoomChip(
  searchFilterUiState: SearchFilterUiState<FilterRoom>,
  onSessionRoomSelected: (FilterRoom, Boolean) -> Unit,
  modifier: Modifier = Modifier,
  onFilterSessionRoomChipClicked: () -> Unit,
) {
  DropdownFilterChip(
    searchFilterUiState = searchFilterUiState,
    onSelected = onSessionRoomSelected,
    filterChipLabelDefaultText = LocalStrings.current.roomTitle,
    onFilterChipClick = onFilterSessionRoomChipClicked,
    dropdownMenuItemText = { sessionRoom ->
      sessionRoom.name
    },
    modifier = modifier.testTag(FilterSessionRoomChipTestTag),
  )
}
