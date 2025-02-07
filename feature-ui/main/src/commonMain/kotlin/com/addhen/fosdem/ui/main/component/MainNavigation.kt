// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Immutable
data class MainNavigationItem(
  val screen: Screen,
  val label: StringResource,
  val contentDescription: StringResource,
  val iconImageVector: ImageVector,
  val selectedImageVector: ImageVector? = null,
)

@Composable
internal fun MainNavigationBar(
  selectedNavigation: Screen,
  navigationItems: List<MainNavigationItem>,
  onNavigationSelected: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  NavigationBar(
    modifier = modifier,
    windowInsets = WindowInsets.navigationBars,
  ) {
    for (item in navigationItems) {
      NavigationBarItem(
        icon = {
          MainNavigationItemIcon(
            item = item,
            selected = selectedNavigation == item.screen,
          )
        },
        label = { Text(text = stringResource(item.label)) },
        selected = selectedNavigation == item.screen,
        onClick = { onNavigationSelected(item.screen) },
      )
    }
  }
}

@Composable
internal fun MainNavigationRail(
  selectedNavigation: Screen,
  navigationItems: List<MainNavigationItem>,
  onNavigationSelected: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  NavigationRail(modifier = modifier) {
    for (item in navigationItems) {
      NavigationRailItem(
        icon = {
          MainNavigationItemIcon(
            item = item,
            selected = selectedNavigation == item.screen,
          )
        },
        alwaysShowLabel = false,
        label = { Text(text = stringResource(item.label)) },
        selected = selectedNavigation == item.screen,
        onClick = { onNavigationSelected(item.screen) },
      )
    }
  }
}

@Composable
internal fun MainNavigationDrawer(
  selectedNavigation: Screen,
  navigationItems: PersistentList<MainNavigationItem>,
  onNavigationSelected: (Screen) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .windowInsetsPadding(WindowInsets.safeContent)
      .padding(16.dp)
      .widthIn(max = 280.dp),
  ) {
    for (item in navigationItems) {
      NavigationDrawerItem(
        icon = {
          Icon(
            imageVector = item.iconImageVector,
            contentDescription = stringResource(item.contentDescription),
          )
        },
        label = { Text(text = stringResource(item.label)) },
        selected = selectedNavigation == item.screen,
        onClick = { onNavigationSelected(item.screen) },
      )
    }
  }
}

@Composable
private fun MainNavigationItemIcon(item: MainNavigationItem, selected: Boolean) {
  if (item.selectedImageVector != null) {
    Crossfade(targetState = selected) { selectedTarget ->
      Icon(
        imageVector = if (selectedTarget) item.selectedImageVector else item.iconImageVector,
        contentDescription = stringResource(item.contentDescription),
      )
    }
  } else {
    Icon(
      imageVector = item.iconImageVector,
      contentDescription = stringResource(item.contentDescription),
    )
  }
}

internal fun Navigator.resetRootIfDifferent(
  screen: Screen,
  saveState: Boolean = false,
  restoreState: Boolean = false,
) {
  val backStack = peekBackStack()
  if (backStack.size > 1 || backStack.lastOrNull() != screen) {
    resetRoot(screen, saveState, restoreState)
  }
}

internal enum class NavigationType {
  BOTTOM_NAVIGATION,
  RAIL,
  PERMANENT_DRAWER,
  ;

  companion object {
    fun forWindowSizeSize(windowSizeClass: WindowSizeClass): NavigationType = when {
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> BOTTOM_NAVIGATION
      windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> RAIL
      else -> PERMANENT_DRAWER
    }
  }
}
