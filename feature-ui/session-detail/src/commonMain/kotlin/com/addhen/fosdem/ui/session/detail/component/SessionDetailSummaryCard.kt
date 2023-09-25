// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.model.api.Event

@Composable
fun SessionDetailSummaryCard(
  dateTitle: String,
  placeTitle: String,
  trackTitle: String,
  sessionItem: Event,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
          1.dp,
        ),
      ),
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 24.dp),
      ) {
        SessionDetailSummaryCardRow(
          leadingIcon = Icons.Outlined.Schedule,
          label = dateTitle,
          content = sessionItem.day.date.toString(),
        )
        SessionDetailSummaryCardRow(
          leadingIcon = Icons.Outlined.Place,
          label = placeTitle,
          content = sessionItem.room.name,
        )
        SessionDetailSummaryCardRow(
          leadingIcon = Icons.Outlined.Category,
          label = trackTitle,
          content = sessionItem.track.name,
        )
      }
    }
  }
}
