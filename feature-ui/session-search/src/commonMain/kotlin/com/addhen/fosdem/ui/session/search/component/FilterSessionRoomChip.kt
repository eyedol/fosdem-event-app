package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Room

const val FilterSessionRoomChipTestTag = "FilterSessionTypeChip"

@Composable
fun FilterSessionRoomChip(
  searchFilterUiState: SearchFilterUiState<Room>,
  onSessionTypeSelected: (Room, Boolean) -> Unit,
  modifier: Modifier = Modifier,
  onFilterSessionTypeChipClicked: () -> Unit,
) {
  DropdownFilterChip(
    searchFilterUiState = searchFilterUiState,
    onSelected = onSessionTypeSelected,
    filterChipLabelDefaultText = LocalStrings.current.roomTitle,
    onFilterChipClick = onFilterSessionTypeChipClicked,
    dropdownMenuItemText = { sessionRoom ->
      sessionRoom.name
    },
    modifier = modifier.testTag(FilterSessionRoomChipTestTag),
  )
}
