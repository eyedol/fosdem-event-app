// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.EmptySessionItems
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.search.component.SearchUiState.Empty
import com.addhen.fosdem.ui.session.search.component.SearchUiState.ListSearch
import kotlinx.collections.immutable.PersistentMap

const val SearchScreenEmptyBodyTestTag = "SearchScreenEmptySearchResultBody"

sealed interface SearchUiState {
  val searchQuery: SearchQuery
  val searchFilterDayUiState: SearchFilterUiState<DayTab>
  val searchFilterSessionTrackUiState: SearchFilterUiState<FilterTrack>
  val searchFilterSessionRoomUiState: SearchFilterUiState<FilterRoom>

  data class Empty(
    override val searchQuery: SearchQuery,
    override val searchFilterDayUiState: SearchFilterUiState<DayTab>,
    override val searchFilterSessionTrackUiState: SearchFilterUiState<FilterTrack>,
    override val searchFilterSessionRoomUiState: SearchFilterUiState<FilterRoom>,

    ) : SearchUiState

  data class ListSearch(
    val sessionItemMap: PersistentMap<String, List<Event>>,
    override val searchQuery: SearchQuery,
    override val searchFilterDayUiState: SearchFilterUiState<DayTab>,
    override val searchFilterSessionTrackUiState: SearchFilterUiState<FilterTrack>,
    override val searchFilterSessionRoomUiState: SearchFilterUiState<FilterRoom>,
  ) : SearchUiState
}

@Composable
fun SessionSearchSheet(
  uiState: SearchUiState,
  scrollState: LazyListState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onDaySelected: (DayTab, Boolean) -> Unit = { _, _ -> },
  onSessionTrackSelected: (FilterTrack, Boolean) -> Unit = { _, _ -> },
  onSessionRoomSelected: (FilterRoom, Boolean) -> Unit = { _, _ -> },
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = Modifier
      .padding(top = contentPadding.calculateTopPadding())
      .then(modifier),
  ) {
    Divider(
      thickness = 1.dp,
      color = MaterialTheme.colorScheme.outline,
    )

    SearchFilter(
      searchFilterDayUiState = uiState.searchFilterDayUiState,
      searchFilterSessionTrackUiState = uiState.searchFilterSessionTrackUiState,
      searchFilterSessionRoomUiState = uiState.searchFilterSessionRoomUiState,
      onDaySelected = onDaySelected,
      onSessionRoomSelected = onSessionRoomSelected,
      onSessionTrackSelected = onSessionTrackSelected,
    )

    when (uiState) {
      is Empty -> {
        val message = LocalStrings.current.searchNotFound(uiState.searchQuery.queryText)
        EmptySessionItems(
          message = message,
          graphicContent = { Text(text = "\uD83D\uDD75️\u200D♂️") },
          modifier = Modifier.testTag(SearchScreenEmptyBodyTestTag),
        )
      }

      is ListSearch -> SearchList(
        contentPadding = PaddingValues(
          bottom = contentPadding.calculateBottomPadding(),
        ),
        scrollState = scrollState,
        sessionItemMap = uiState.sessionItemMap,
        searchQuery = uiState.searchQuery,
        onSessionItemClick = onSessionItemClick,
        onBookmarkIconClick = onBookmarkClick,
      )
    }
  }
}
