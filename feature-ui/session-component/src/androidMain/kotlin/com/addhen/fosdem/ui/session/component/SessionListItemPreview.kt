// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day1Event

@MultiThemePreviews
@Composable
private fun SessionListItemPreview() {
  AppTheme {
    Surface {
      SessionListItem(
        sessionItem = day1Event,
        isBookmarked = true,
        onBookmarkClick = { _, _ -> },
        chipContent = {},
        highlightQuery = SearchQuery.Empty,
      )
    }
  }
}
