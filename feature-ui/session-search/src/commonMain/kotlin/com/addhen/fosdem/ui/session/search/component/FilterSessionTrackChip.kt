package com.addhen.fosdem.ui.session.search.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Track

const val FilterSessionTrackChipTestTag = "FilterSessionTrackChip"

@Composable
fun FilterSessionTrackChip(
  searchFilterUiState: SearchFilterUiState<Track>,
  onSessionTracksSelected: (Track, Boolean) -> Unit,
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
