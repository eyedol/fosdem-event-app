// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.ui.about.component.AboutSummaryCardRow

@MultiThemePreviews
@Composable
private fun AboutFooterLinksIconPreview() {
  AppTheme {
    Surface {
      AboutSummaryCardRow(
        leadingIcon = Icons.Filled.Schedule,
        label = "label".repeat(5),
        content = "content".repeat(5),
      )
    }
  }
}
