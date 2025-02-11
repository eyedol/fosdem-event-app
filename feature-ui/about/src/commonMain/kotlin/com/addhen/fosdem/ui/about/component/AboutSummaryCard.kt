// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.date_description
import com.addhen.fosdem.compose.common.ui.api.date_title
import com.addhen.fosdem.compose.common.ui.api.place_description
import com.addhen.fosdem.compose.common.ui.api.place_link
import com.addhen.fosdem.model.api.formatWithDayName
import com.addhen.fosdem.model.api.saturday
import com.addhen.fosdem.model.api.sunday
import com.slack.circuit.retained.rememberRetained
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutSummaryCard(
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {

  val saturdayText by rememberRetained { mutableStateOf(saturday.formatWithDayName()) }
  val sundayText by rememberRetained { mutableStateOf(sunday.formatWithDayName()) }

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
        label = stringResource(Res.string.date_title),
        content = stringResource(Res.string.date_description, saturdayText, sundayText),
      )
      AboutSummaryCardRow(
        leadingIcon = Icons.Outlined.Place,
        label = stringResource(Res.string.place_description),
        content = stringResource(Res.string.place_link),
        onLinkClick = onLinkClick,
        url = "https://www.openstreetmap.org/?mlat=50.81238&mlon=4.38073#map=18/50.81238/4.38073",
      )
    }
  }
}
