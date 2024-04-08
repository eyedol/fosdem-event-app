// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@MultiThemePreviews
@Composable
private fun AutoSizedCircularProgressIndicatorPreview() {
  AppTheme {
    Surface {
      Column {
        AutoSizedCircularProgressIndicator(
          modifier = Modifier.size(16.dp),
        )

        AutoSizedCircularProgressIndicator(
          modifier = Modifier.size(24.dp),
        )

        AutoSizedCircularProgressIndicator(
          modifier = Modifier.size(48.dp),
        )

        AutoSizedCircularProgressIndicator(
          modifier = Modifier.size(64.dp),
        )

        AutoSizedCircularProgressIndicator(
          modifier = Modifier.size(128.dp),
        )
      }
    }
  }
}
