// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.room_title
import com.addhen.fosdem.ui.session.component.FilterRoom
import org.jetbrains.compose.resources.stringResource

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
    filterChipLabelDefaultText = stringResource(Res.string.room_title),
    onFilterChipClick = onFilterSessionRoomChipClicked,
    dropdownMenuItemText = { sessionRoom -> sessionRoom.name },
    modifier = modifier.testTag(FilterSessionRoomChipTestTag),
  )
}
