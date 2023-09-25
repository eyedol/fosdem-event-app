// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.model.api.day1Event

@MultiThemePreviews
@Composable
fun SessionDetailBottomAppBarPreview() {
  val appString = EnAppStrings
  AppTheme {
    Surface {
      SessionDetailBottomAppBar(
        event = day1Event,
        isBookmarked = true,
        addFavorite = appString.addToFavoritesTitle,
        removeFavorite = appString.removeFromFavorites,
        shareTitle = appString.shareTitle,
        addToCalendar = appString.addToCalendarTitle,
        onBookmarkClick = { _, _ -> },
        onCalendarRegistrationClick = { _ -> },
        onShareClick = {},
      )
    }
  }
}
