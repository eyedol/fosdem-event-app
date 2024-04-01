// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.LocalConferenceInfo
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.imageVectorResource

@Composable
fun AboutDetail(
  abountImageResource: ImageVectorResource,
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {
  val appStrings = LocalStrings.current
  val conferenceInfo = LocalConferenceInfo.current
  Column(
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(16.dp),
      contentAlignment = Alignment.Center,
    ) {
      Column {
        Image(
          imageVector = imageVectorResource(abountImageResource),
          contentDescription = null,
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .width(200.dp),
        )
        Text(
          text = "${conferenceInfo.name} ${conferenceInfo.fullYear}",
          style = MaterialTheme.typography.headlineLarge,
          modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp),
        )
      }

    }
    Text(
      text = appStrings.aboutFosdem,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier
        .padding(
          start = 16.dp,
          end = 16.dp,
          bottom = 12.dp,
        ),
    )
    AboutSummaryCard(
      modifier = Modifier
        .padding(
          start = 16.dp,
          top = 12.dp,
          end = 16.dp,
        ),
      onLinkClick = onLinkClick,
    )
  }
}
