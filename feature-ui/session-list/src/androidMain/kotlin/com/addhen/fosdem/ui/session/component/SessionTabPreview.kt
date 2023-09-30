// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.ui.session.list.component.SessionTab
import com.addhen.fosdem.ui.session.list.component.SessionTabRow
import com.addhen.fosdem.ui.session.list.component.rememberSessionTabState

@MultiThemePreviews
@Composable
fun SessionTabRowPreview() {
  val scrollState = rememberSessionTabState()
  val selectedTabIndex = 0
  AppTheme {
    Surface {
      SessionTabRow(tabState = scrollState, selectedTabIndex = selectedTabIndex) {
        (0..1).forEachIndexed { index, _ ->
          SessionTab(
            tabTitle = if (index == 0) "Saturday" else "Sunday",
            selected = selectedTabIndex == index,
            onClick = {},
          )
        }
      }
    }
  }
}
