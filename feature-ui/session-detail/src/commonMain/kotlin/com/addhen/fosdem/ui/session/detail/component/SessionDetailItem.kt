// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.model.api.Event

data class SessionDetailItemSectionUiState(
  val event: Event,
  val dateTitle: String,
  val placeTitle: String,
  val trackTitle: String,
  val readMoreTitle: String,
)

@Composable
internal fun ScreenDetailItem(
  uiState: SessionDetailItemSectionUiState,
  contentPadding: PaddingValues,
  onLinkClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = contentPadding,
  ) {
    item {
      SessionDetailSummaryCard(
        dateTitle = uiState.dateTitle,
        placeTitle = uiState.placeTitle,
        trackTitle = uiState.trackTitle,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
        sessionItem = uiState.event,
      )
    }

    item {
      SessionDetailContent(
        uiState = uiState,
        onLinkClick = onLinkClick,
      )
    }
  }
}
