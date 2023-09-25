// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@MultiThemePreviews
@Composable
fun SessionDetailTopAppBarPreview() {
  AppTheme {
    Surface {
      SessionDetailTopAppBar(
        "How regulating software for the european market could impact FOSS",
        onNavigationIconClick = {},
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
      )
    }
  }
}
