// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.model.api.day1Event

@MultiThemePreviews
@Composable
fun SessionDetailItemPreview() {
  val appString = EnAppStrings
  val uiState = SessionDetailItemSectionUiState(
    event = day1Event,
    dateTitle = appString.dateTitle,
    placeTitle = appString.roomTitle,
    trackTitle = appString.trackTitle,
    readMoreTitle = appString.readMoreLabel,
  )
  AppTheme {
    Surface {
      SessionDetailItem(uiState = uiState, contentPadding = PaddingValues(), onLinkClick = {})
    }
  }
}
