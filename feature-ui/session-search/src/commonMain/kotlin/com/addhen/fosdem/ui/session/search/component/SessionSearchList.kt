// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import co.touchlab.kermit.Logger
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.SessionList
import com.addhen.fosdem.ui.session.component.SessionListUiState
import kotlinx.collections.immutable.PersistentMap

@Composable
fun SearchList(
  scrollState: LazyListState,
  sessionItemMap: PersistentMap<String, List<Event>>,
  searchQuery: SearchQuery,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkIconClick: (Long, Boolean) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  var hasFocusCleared by remember { mutableStateOf(false) }
  if (scrollState.isScrollInProgress && hasFocusCleared.not()) {
    LocalFocusManager.current.clearFocus()
  } else {
    hasFocusCleared = false
  }
  SessionList(
    SessionListUiState(sessionItemMap),
    scrollState,
    onBookmarkIconClick,
    onSessionItemClick,
    highlightQuery = searchQuery,
    shouldShowDayTitle = true,
    contentPadding,
    modifier,
  )
}
