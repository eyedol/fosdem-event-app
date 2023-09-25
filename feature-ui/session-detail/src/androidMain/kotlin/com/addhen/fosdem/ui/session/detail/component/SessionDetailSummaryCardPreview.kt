package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.model.api.day1Event

@MultiThemePreviews
@Composable
fun SessionDetailSummaryCardPreview() {
  val appString = EnAppStrings
  AppTheme {
    Surface {
      SessionDetailSummaryCard(
        dateTitle = appString.dateTitle,
        placeTitle = appString.roomTitle,
        trackTitle = appString.trackTitle,
        sessionItem = day1Event)
    }
  }
}
