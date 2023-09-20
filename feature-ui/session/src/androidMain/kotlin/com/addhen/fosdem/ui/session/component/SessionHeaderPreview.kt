// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.compose.common.ui.api.painterResource
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.ui.session.R

@MultiThemePreviews
@Composable
fun SessionHeaderPreview() {
  AppTheme {
    Surface {
      SessionHeader(
        painter = painterResource(
          ImageResource(R.drawable.fosdem_logo),
        ),
      )
    }
  }
}
