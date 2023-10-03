// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@MultiThemePreviews
@Composable
fun SessionHeaderPreview() {
  AppTheme {
    Surface {
      SearchFilters(
        isAll = false,
        isDayFirst = true,
        isDaySecond = false,
        onAllFilterChipClick = {},
        onDayFirstChipClick = {},
        onDaySecondChipClick = {},
      )
    }
  }
}
