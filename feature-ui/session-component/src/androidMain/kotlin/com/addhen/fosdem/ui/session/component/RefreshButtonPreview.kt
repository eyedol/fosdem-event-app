// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@MultiThemePreviews
@Composable
fun RefreshButtonPreview() {
  AppTheme {
    Surface {
      Column {
        RefreshButton(refreshing = false, onClick = { /*TODO*/ })
        RefreshButton(refreshing = true, onClick = { /*TODO*/ })
      }
    }
  }
}
