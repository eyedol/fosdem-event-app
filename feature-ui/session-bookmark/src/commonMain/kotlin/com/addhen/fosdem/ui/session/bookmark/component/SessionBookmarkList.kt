// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.addhen.fosdem.ui.session.component.SessionList
import com.addhen.fosdem.ui.session.component.SessionListUiState

@Composable
fun BookmarkList(
  scrollState: LazyListState,
  uiState: SessionListUiState,
  onSessionItemClick: (Long) -> Unit,
  onBookmarkIconClick: (Long, Boolean) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) = SessionList(
  uiState,
  scrollState,
  onBookmarkIconClick,
  onSessionItemClick,
  contentPadding,
  modifier,
)
