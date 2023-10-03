// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.ui.session.component.FilterTrack

const val FilterSessionTrackChipTestTag = "FilterSessionTrackChip"

@Composable
fun FilterSessionTrackChip(
  searchFilterUiState: SearchFilterUiState<FilterTrack>,
  onSessionTracksSelected: (FilterTrack, Boolean) -> Unit,
  modifier: Modifier = Modifier,
  onFilterSessionTrackChipClicked: () -> Unit,
) {
  DropdownFilterChip(
    searchFilterUiState = searchFilterUiState,
    onSelected = onSessionTracksSelected,
    filterChipLabelDefaultText = LocalStrings.current.trackTitle,
    onFilterChipClick = onFilterSessionTrackChipClicked,
    dropdownMenuItemText = { track ->
      track.name
    },
    modifier = modifier.testTag(FilterSessionTrackChipTestTag),
  )
}
