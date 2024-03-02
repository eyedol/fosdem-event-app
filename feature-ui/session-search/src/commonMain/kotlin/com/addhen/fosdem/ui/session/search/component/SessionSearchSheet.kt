// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.EmptySessionItems
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.SessionShimmerList
import com.addhen.fosdem.ui.session.search.component.SearchUiState.ListSearch
import com.addhen.fosdem.ui.session.search.component.SearchUiState.Loading
import kotlinx.collections.immutable.PersistentMap

const val SearchScreenEmptyBodyTestTag = "SearchScreenEmptySearchResultBody"

sealed interface SearchUiState {
  val query: SearchQuery
  val filterDayUiState: SearchFilterUiState<DayTab>
  val filterTrackUiState: SearchFilterUiState<FilterTrack>
  val filterRoomUiState: SearchFilterUiState<FilterRoom>

  data class Empty(
    override val query: SearchQuery,
    override val filterDayUiState: SearchFilterUiState<DayTab>,
    override val filterTrackUiState: SearchFilterUiState<FilterTrack>,
    override val filterRoomUiState: SearchFilterUiState<FilterRoom>,
  ) : SearchUiState
  data class Loading(
    override val query: SearchQuery = SearchQuery(""),
    override val filterDayUiState: SearchFilterUiState<DayTab> = SearchFilterUiState(),
    override val filterTrackUiState: SearchFilterUiState<FilterTrack> = SearchFilterUiState(),
    override val filterRoomUiState: SearchFilterUiState<FilterRoom> = SearchFilterUiState(),
  ) : SearchUiState

  data class ListSearch(
    val sessionItemMap: PersistentMap<String, List<Event>>,
    override val query: SearchQuery,
    override val filterDayUiState: SearchFilterUiState<DayTab>,
    override val filterTrackUiState: SearchFilterUiState<FilterTrack>,
    override val filterRoomUiState: SearchFilterUiState<FilterRoom>,
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
  val layoutDirection = LocalLayoutDirection.current
  Column(
    modifier = Modifier
      .padding(top = contentPadding.calculateTopPadding())
      .then(modifier),
  ) {
      HorizontalDivider(
          thickness = 1.dp,
          color = MaterialTheme.colorScheme.outline
      )

      SearchFilter(
          searchFilterDayUiState = uiState.filterDayUiState,
          searchFilterSessionTrackUiState = uiState.filterTrackUiState,
          searchFilterSessionRoomUiState = uiState.filterRoomUiState,
          onDaySelected = onDaySelected,
          onSessionRoomSelected = onSessionRoomSelected,
          onSessionTrackSelected = onSessionTrackSelected,
      )

      when (uiState) {
          is Loading -> {
              SessionShimmerList(
                  modifier = Modifier
                      .weight(1f)
                      .fillMaxSize(),
                  contentPadding = PaddingValues(
                      bottom = contentPadding.calculateBottomPadding(),
                      start = contentPadding.calculateStartPadding(layoutDirection),
                      end = contentPadding.calculateEndPadding(layoutDirection),
                  ),
              )
          }

          is ListSearch -> SearchList(
              contentPadding = PaddingValues(
                  bottom = contentPadding.calculateBottomPadding(),
              ),
              scrollState = scrollState,
              sessionItemMap = uiState.sessionItemMap,
              searchQuery = uiState.query,
              onSessionItemClick = onSessionItemClick,
              onBookmarkIconClick = onBookmarkClick,
          )

          is SearchUiState.Empty -> {
              val message = LocalStrings.current.searchNotFound(uiState.query.queryText)
              EmptySessionItems(
                  message = message,
                  graphicContent = { Text(text = "\uD83D\uDD75️\u200D♂️") },
                  modifier = Modifier.testTag(SearchScreenEmptyBodyTestTag),
              )
          }
      }
  }
}
