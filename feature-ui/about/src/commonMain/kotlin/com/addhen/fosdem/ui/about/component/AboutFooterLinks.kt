// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.AppImage
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.imageResource

const val AboutFooterLinksFacebookItemTestTag = "AboutFooterLinksFacebookItem"
const val AboutFooterLinksXItemTestTag = "AboutFooterLinksXItem"
const val AboutFooterLinksMastadonItemTestTag = "AboutFooterLinksMastadonItem"
const val AboutFooterLinksInstagramItemTestTag = "AboutFooterLinksInstagramItem"

@Composable
fun AboutFooterLinks(
  versionName: String?,
  onMastadonClick: () -> Unit,
  onXClick: () -> Unit,
  onInstagramClick: () -> Unit,
  onFacebookClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val appString = LocalStrings.current
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 24.dp, bottom = 16.dp),
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      AboutFooterLinksIcon(
        iconResourceImage = imageResource(AppImage.XLogo),
        testTag = AboutFooterLinksXItemTestTag,
        contentDescription = "X",
        onClick = onXClick,
      )
      AboutFooterLinksIcon(
        iconResourceImage = imageResource(AppImage.FacebookLogo),
        testTag = AboutFooterLinksFacebookItemTestTag,
        contentDescription = "Facebook",
        onClick = onFacebookClick,
      )
      AboutFooterLinksIcon(
        iconResourceImage = imageResource(AppImage.InstagramLogo),
        testTag = AboutFooterLinksInstagramItemTestTag,
        contentDescription = "Instagram",
        onClick = onInstagramClick,
      )
      AboutFooterLinksIcon(
        iconResourceImage = imageResource(AppImage.MastadonLogo),
        testTag = AboutFooterLinksMastadonItemTestTag,
        contentDescription = "Mastadon",
        onClick = onMastadonClick,
      )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
      text = appString.appVersion,
      style = MaterialTheme.typography.labelLarge,
    )
    if (versionName != null) {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = versionName,
        style = MaterialTheme.typography.labelLarge,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
  }
}
