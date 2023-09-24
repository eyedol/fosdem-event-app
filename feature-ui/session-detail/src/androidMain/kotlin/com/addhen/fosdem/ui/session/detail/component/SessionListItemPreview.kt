// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.ui.session.detail.component.SessionListItem

@MultiThemePreviews
@Composable
fun SessionListItemPreview() {
  AppTheme {
    Surface {
      SessionListItem(
        sessionItem = day1Event,
        addSessionFavoriteContentDescription = "Add session to favorites",
        removeSessionFavoriteContentDescription = "Remove session favorites",
        isBookmarked = true,
        onBookmarkClick = { _, _ -> },
        chipContent = {},
      )
    }
  }
}
