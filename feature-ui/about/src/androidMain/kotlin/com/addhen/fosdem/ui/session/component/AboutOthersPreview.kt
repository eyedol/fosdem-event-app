// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.license_title
import com.addhen.fosdem.compose.common.ui.api.privacy_policy_title
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.ui.about.component.aboutOthers

@MultiThemePreviews
@Composable
private fun AboutOthersPreview() {
  AppTheme {
    Surface {
      LazyColumn {
        aboutOthers(
          Res.string.license_title,
          Res.string.privacy_policy_title,
          onLicenseItemClick = {},
          onPrivacyPolicyItemClick = {},
        )
      }
    }
  }
}
