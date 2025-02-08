// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

const val AboutOthersLicenseItemTestTag = "AboutOthersLicenseItem"
const val AboutOthersPrivacyPolicyItemTestTag = "AboutOthersPrivacyPolicyItem"

fun LazyListScope.aboutOthers(
  licenseLabel: StringResource,
  privacyPolicy: StringResource,
  onLicenseItemClick: () -> Unit,
  onPrivacyPolicyItemClick: () -> Unit,
) {
  item {
    AboutContentColumn(
      leadingIcon = Icons.Outlined.FileCopy,
      label = stringResource(licenseLabel),
      testTag = AboutOthersLicenseItemTestTag,
      onClickAction = onLicenseItemClick,
      modifier = Modifier
        .padding(
          horizontal = 16.dp,
        ),
    )
  }
  item {
    AboutContentColumn(
      leadingIcon = Icons.Outlined.PrivacyTip,
      label = stringResource(privacyPolicy),
      testTag = AboutOthersPrivacyPolicyItemTestTag,
      onClickAction = onPrivacyPolicyItemClick,
      modifier = Modifier
        .padding(
          horizontal = 16.dp,
        ),
    )
  }
}
