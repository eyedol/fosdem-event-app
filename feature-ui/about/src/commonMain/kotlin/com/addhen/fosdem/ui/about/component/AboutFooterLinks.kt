// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@Composable
fun AboutFooterLinks(
  versionName: String?,
  modifier: Modifier = Modifier,
) {
  val appString = LocalStrings.current
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 24.dp, bottom = 16.dp),
  ) {
    Text(
      text = appString.appVersion,
      style = MaterialTheme.typography.labelLarge,
    )
    if (versionName != null) {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = versionName,
        style = MaterialTheme.typography.labelLarge,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
  }
}
