// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.theme.trackColors

@MultiThemePreviews
@Composable
fun EmptySessionItemsPreview() {
  AppTheme {
    Surface {
      EmptySessionItems(
        message = "No session found",
        graphicContent = {
          Text(text = "\uD83D\uDD75️\u200D♂️")
        }
      )
    }
  }
}
