// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.ui.main.buildNavigationItems

@MultiThemePreviews
@Composable
private fun MainNavigationPreview() {
  val strings = EnAppStrings
  val navigationItems = remember(strings) { buildNavigationItems(strings) }
  AppTheme {
    Surface {
      Scaffold(
        bottomBar = {
          MainNavigationBar(
            selectedNavigation = SessionScreen,
            navigationItems = navigationItems,
            onNavigationSelected = {},
            modifier = Modifier.fillMaxWidth(),
          )
        },
        // We let content handle the status bar
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
          .exclude(WindowInsets.statusBars),
        modifier = Modifier,
      ) { paddingValues ->
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        ) {
        }
      }
    }
  }
}
