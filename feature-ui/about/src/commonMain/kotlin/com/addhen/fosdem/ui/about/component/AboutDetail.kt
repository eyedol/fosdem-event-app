// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.imageVectorResource

@Composable
fun AboutDetail(
  abountImageResource: ImageVectorResource,
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {
  val appStrings = LocalStrings.current
  Column(
    modifier = modifier,
  ) {
    Image(
      imageVector = imageVectorResource(abountImageResource),
      contentDescription = null,
      contentScale = ContentScale.FillWidth,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    )
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
