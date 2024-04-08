// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.imageVectorResource

@Composable
fun AboutFooterLinksIcon(
  iconResourceImage: ImageVectorResource,
  testTag: String,
  contentDescription: String,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
    modifier = modifier
      .testTag(testTag)
      .size(48.dp),
  ) {
    Image(
      imageVector = imageVectorResource(iconResourceImage),
      contentDescription = contentDescription,
      contentScale = ContentScale.FillWidth,
    )
  }
}
