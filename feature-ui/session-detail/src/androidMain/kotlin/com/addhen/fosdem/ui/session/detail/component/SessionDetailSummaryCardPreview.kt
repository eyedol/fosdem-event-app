// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.date_title
import com.addhen.fosdem.compose.common.ui.api.room_title
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.track_title
import com.addhen.fosdem.model.api.day1Event
import org.jetbrains.compose.resources.stringResource

@MultiThemePreviews
@Composable
private fun SessionDetailSummaryCardPreview() {
  AppTheme {
    Surface {
      SessionDetailSummaryCard(
        dateTitle = stringResource(Res.string.date_title),
        roomTitle = stringResource(Res.string.room_title),
        trackTitle = stringResource(Res.string.track_title),
        sessionItem = day1Event,
      )
    }
  }
}
