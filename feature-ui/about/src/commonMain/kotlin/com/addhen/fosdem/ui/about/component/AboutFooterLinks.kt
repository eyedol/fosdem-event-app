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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.app_version
import com.addhen.fosdem.compose.common.ui.api.fosdem_disclaimer
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutFooterLinks(
  versionName: String?,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 24.dp, bottom = 16.dp),
  ) {
    Text(
      text = stringResource(Res.string.app_version),
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
    Text(
      modifier = Modifier.padding(horizontal = 12.dp),
      text = stringResource(Res.string.fosdem_disclaimer),
      style = MaterialTheme.typography.labelSmall,
      textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(8.dp))
  }
}
