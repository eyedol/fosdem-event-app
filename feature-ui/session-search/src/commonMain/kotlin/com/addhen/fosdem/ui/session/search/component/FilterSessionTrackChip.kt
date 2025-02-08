// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.track_title
import com.addhen.fosdem.ui.session.component.FilterTrack
import org.jetbrains.compose.resources.stringResource

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
    filterChipLabelDefaultText = stringResource(Res.string.track_title),
    onFilterChipClick = onFilterSessionTrackChipClicked,
    dropdownMenuItemText = { track ->
      track.name
    },
    modifier = modifier.testTag(FilterSessionTrackChipTestTag),
  )
}
