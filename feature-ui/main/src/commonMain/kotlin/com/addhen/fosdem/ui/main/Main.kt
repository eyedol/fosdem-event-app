// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.LocalWindowSizeClass
import com.addhen.fosdem.core.api.i18n.AppStrings
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.ui.main.component.MainNavigationBar
import com.addhen.fosdem.ui.main.component.MainNavigationDrawer
import com.addhen.fosdem.ui.main.component.MainNavigationItem
import com.addhen.fosdem.ui.main.component.MainNavigationRail
import com.addhen.fosdem.ui.main.component.NavigationType
import com.addhen.fosdem.ui.main.component.resetRootIfDifferent
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import kotlinx.collections.immutable.toPersistentList
import me.tatarka.inject.annotations.Inject

@Inject
class MainUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = null
}

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

  val strings = LocalStrings.current
  val navigationItems = remember(strings) { buildNavigationItems(strings) }

  Scaffold(
    bottomBar = {
      if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
        MainNavigationBar(
          selectedNavigation = rootScreen,
          navigationItems = navigationItems,
          onNavigationSelected = { navigator.resetRootIfDifferent(it, backstack) },
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
          onNavigationSelected = { navigator.resetRootIfDifferent(it, backstack) },
          modifier = Modifier.fillMaxHeight(),
        )

        Divider(
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
          backstack = backstack,
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

fun buildNavigationItems(strings: AppStrings): List<MainNavigationItem> {
  return listOf(
    MainNavigationItem(
      screen = SessionScreen,
      label = strings.sessionTitle,
      contentDescription = strings.sessionContentDescription,
      iconImageVector = Icons.Outlined.CalendarMonth,
      selectedImageVector = Icons.Filled.CalendarMonth,
    ),
    MainNavigationItem(
      screen = SessionScreen,
      label = strings.bookmarkTitle,
      contentDescription = strings.bookmarkContentDescription,
      iconImageVector = Icons.Outlined.Bookmark,
      selectedImageVector = Icons.Filled.Bookmark,
    ),
    MainNavigationItem(
      screen = SessionScreen,
      label = strings.mapTitle,
      contentDescription = strings.mapContentDescription,
      iconImageVector = Icons.Outlined.Map,
      selectedImageVector = Icons.Filled.Map,
    ),
    MainNavigationItem(
      screen = SessionScreen,
      label = strings.aboutTitle,
      contentDescription = strings.aboutContentDescription,
      iconImageVector = Icons.Outlined.Info,
      selectedImageVector = Icons.Filled.Info,
    ),
  )
}
