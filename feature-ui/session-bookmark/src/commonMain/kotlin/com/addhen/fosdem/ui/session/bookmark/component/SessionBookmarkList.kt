// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.component.SessionList
import com.addhen.fosdem.ui.session.component.SessionListUiState
import kotlinx.collections.immutable.PersistentMap

@Composable
fun BookmarkList(
  scrollState: LazyListState,
  sessionItemMap: PersistentMap<String, List<Event>>,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkIconClick: (Long, Boolean) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) = SessionList(
  SessionListUiState(sessionItemMap),
  scrollState,
  onBookmarkIconClick,
  onSessionItemClick,
  contentPadding = contentPadding,
  modifier = modifier,
)
