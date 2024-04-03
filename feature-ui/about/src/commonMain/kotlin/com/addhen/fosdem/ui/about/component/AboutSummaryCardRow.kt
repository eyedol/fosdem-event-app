// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.ClickableLinkText
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@Composable
fun AboutSummaryCardRow(
  leadingIcon: ImageVector,
  label: String,
  content: String,
  url: String? = null,
  modifier: Modifier = Modifier,
  leadingIconContentDescription: String? = null,
  onLinkClick: (url: String) -> Unit = {},
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
  ) {
    Icon(
      imageVector = leadingIcon,
      contentDescription = leadingIconContentDescription,
      modifier = Modifier.size(16.dp),
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(
        text = label,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.labelLarge,
      )
      Spacer(modifier = Modifier.width(12.dp))
      ClickableLinkText(
        style = MaterialTheme.typography.bodyMedium,
        content = content,
        onLinkClick = onLinkClick,
        regex = content.toRegex(),
        url = url,
      )
    }
  }
}
