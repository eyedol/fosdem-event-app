// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@Composable
fun AboutSummaryCard(
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {
  val appStrings = LocalStrings.current
  Card(
    shape = RoundedCornerShape(12.dp),
    modifier = modifier,
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
      AboutSummaryCardRow(
        leadingIcon = Icons.Outlined.Schedule,
        label = appStrings.dateTitle,
        content = appStrings.dateDescription,
      )
      val placeContent = appStrings.placeDescription
        .plus(" " + appStrings.placeLink)
      AboutSummaryCardRow(
        leadingIcon = Icons.Outlined.Place,
        label = appStrings.placeDescription,
        content = placeContent,
        onLinkClick = onLinkClick,
      )
    }
  }
}
