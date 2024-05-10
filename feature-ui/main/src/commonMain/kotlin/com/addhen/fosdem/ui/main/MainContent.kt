// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.addhen.fosdem.compose.common.ui.api.LocalWindowSizeClass
import com.addhen.fosdem.compose.common.ui.api.ProvideStrings
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.core.api.screens.CalendarScreen
import com.addhen.fosdem.core.api.screens.ShareScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.tatarka.inject.annotations.Inject

interface AppContent {

  @Composable
  fun Content(
    backstack: SaveableBackStack,
    navigator: Navigator,
    onOpenUrl: (String) -> Unit,
    onShare: (String) -> Unit,
    onCalendarShare: (String, String, String, Long, Long) -> Unit,
    modifier: Modifier,
  )
}

@Inject
class MainContent(private val circuitConfig: Circuit) : AppContent {

  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @Composable
  override fun Content(
    backstack: SaveableBackStack,
    navigator: Navigator,
    onOpenUrl: (String) -> Unit,
    onShare: (String) -> Unit,
    onCalendarShare: (String, String, String, Long, Long) -> Unit,
    modifier: Modifier,
  ) {
    val appNavigator: Navigator = remember(navigator) {
      AppNavigator(navigator, onOpenUrl, onShare, onCalendarShare)
    }

    ProvideStrings {
      CompositionLocalProvider(
        LocalNavigator provides appNavigator,
        LocalWindowSizeClass provides calculateWindowSizeClass(),
      ) {
        CircuitCompositionLocals(circuitConfig) {
          AppTheme(
            useDynamicColors = false,
          ) {
            Main(
              backstack = backstack,
              navigator = appNavigator,
              modifier = modifier,
            )
          }
        }
      }
    }
  }
}

private class AppNavigator(
  private val navigator: Navigator,
  private val onOpenUrl: (String) -> Unit,
  private val onShare: (String) -> Unit,
  private val onCalendarShare: (String, String, String, Long, Long) -> Unit,
) : Navigator {
  override fun goTo(screen: Screen) {
    when (screen) {
      is UrlScreen -> onOpenUrl(screen.url)
      is ShareScreen -> onShare(screen.info)
      is CalendarScreen -> onCalendarShare(
        screen.title,
        screen.room,
        screen.description,
        screen.startAtMillSeconds,
        screen.endAtMillSeconds,
      )
      else -> navigator.goTo(screen)
    }
  }

  override fun peek(): Screen? = navigator.peek()

  override fun peekBackStack(): ImmutableList<Screen> = navigator.peekBackStack().toImmutableList()

  override fun pop(result: PopResult?): Screen? = navigator.pop(result)

  override fun resetRoot(
    newRoot: Screen,
    saveState: Boolean,
    restoreState: Boolean,
  ): ImmutableList<Screen> =
    navigator.resetRoot(newRoot, saveState, restoreState).toImmutableList()
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { Navigator.NoOp }
