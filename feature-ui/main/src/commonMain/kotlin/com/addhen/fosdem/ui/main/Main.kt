// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalWindowSizeClass
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.about_content_description
import com.addhen.fosdem.compose.common.ui.api.about_title
import com.addhen.fosdem.compose.common.ui.api.map_content_description
import com.addhen.fosdem.compose.common.ui.api.map_title
import com.addhen.fosdem.compose.common.ui.api.search_content_description
import com.addhen.fosdem.compose.common.ui.api.search_title
import com.addhen.fosdem.compose.common.ui.api.session_content_description
import com.addhen.fosdem.compose.common.ui.api.session_title
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.core.api.screens.MapScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.ui.main.component.MainNavigationBar
import com.addhen.fosdem.ui.main.component.MainNavigationDrawer
import com.addhen.fosdem.ui.main.component.MainNavigationItem
import com.addhen.fosdem.ui.main.component.MainNavigationRail
import com.addhen.fosdem.ui.main.component.NavigationType
import com.addhen.fosdem.ui.main.component.resetRootIfDifferent
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.Navigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import kotlinx.collections.immutable.toPersistentList

@Composable
fun Main(
  backstack: SaveableBackStack,
  navigator: Navigator,
  modifier: Modifier = Modifier,
) {
  val windowSizeClass = LocalWindowSizeClass.current
  val navigationType = remember(windowSizeClass) {
    NavigationType.forWindowSizeSize(windowSizeClass)
  }

  val rootScreen by remember(backstack) {
    derivedStateOf { backstack.last().screen }
  }

  val navigationItems = remember { buildNavigationItems() }

  Scaffold(
    bottomBar = {
      if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
        MainNavigationBar(
          selectedNavigation = rootScreen,
          navigationItems = navigationItems,
          onNavigationSelected = {
            navigator.resetRootIfDifferent(it, saveState = true, restoreState = true)
          },
          modifier = Modifier.fillMaxWidth(),
        )
      } else {
        Spacer(
          Modifier
            .windowInsetsBottomHeight(WindowInsets.navigationBars)
            .fillMaxWidth(),
        )
      }
    },
    // We let content handle the status bar
    contentWindowInsets = ScaffoldDefaults.contentWindowInsets
      .exclude(WindowInsets.statusBars),
    modifier = modifier,
  ) { paddingValues ->
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
    ) {
      if (navigationType == NavigationType.RAIL) {
        MainNavigationRail(
          selectedNavigation = rootScreen,
          navigationItems = navigationItems,
          onNavigationSelected = {
            navigator.resetRootIfDifferent(it, saveState = true, restoreState = true)
          },
          modifier = Modifier.fillMaxHeight(),
        )

        HorizontalDivider(
          Modifier
            .fillMaxHeight()
            .width(1.dp),
        )
      } else if (navigationType == NavigationType.PERMANENT_DRAWER) {
        MainNavigationDrawer(
          selectedNavigation = rootScreen,
          navigationItems = navigationItems.toPersistentList(),
          onNavigationSelected = { navigator.resetRoot(it) },
          modifier = Modifier.fillMaxHeight(),
        )
      }

      ContentWithOverlays {
        NavigableCircuitContent(
          navigator = navigator,
          backStack = backstack,
          decoration = remember(navigator) {
            GestureNavigationDecoration(onBackInvoked = navigator::pop)
          },
          modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        )
      }
    }
  }
}

fun buildNavigationItems(): List<MainNavigationItem> {
  return listOf(
    MainNavigationItem(
      screen = SessionsScreen,
      label = Res.string.session_title,
      contentDescription = Res.string.session_content_description,
      iconImageVector = Icons.Outlined.CalendarMonth,
      selectedImageVector = Icons.Filled.CalendarMonth,
    ),
    MainNavigationItem(
      screen = SessionSearchScreen,
      label = Res.string.search_title,
      contentDescription = Res.string.search_content_description,
      iconImageVector = Icons.Outlined.Search,
      selectedImageVector = Icons.Filled.Search,
    ),
    MainNavigationItem(
      screen = MapScreen,
      label = Res.string.map_title,
      contentDescription = Res.string.map_content_description,
      iconImageVector = Icons.Outlined.Map,
      selectedImageVector = Icons.Filled.Map,
    ),
    MainNavigationItem(
      screen = AboutScreen,
      label = Res.string.about_title,
      contentDescription = Res.string.about_content_description,
      iconImageVector = Icons.Outlined.Info,
      selectedImageVector = Icons.Filled.Info,
    ),
  )
}
