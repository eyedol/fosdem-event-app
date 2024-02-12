// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
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
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias MainContent = @Composable (
  backstack: SaveableBackStack,
  navigator: Navigator,
  onOpenUrl: (String) -> Unit,
  modifier: Modifier,
) -> Unit

@Inject
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainContent(
  @Assisted backstack: SaveableBackStack,
  @Assisted navigator: Navigator,
  @Assisted onOpenUrl: (String) -> Unit,
  circuitConfig: Circuit,
  @Assisted modifier: Modifier = Modifier,
) {
  val appNavigator: Navigator = remember(navigator) {
    AppNavigator(navigator, onOpenUrl)
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

private class AppNavigator(
  private val navigator: Navigator,
  private val onOpenUrl: (String) -> Unit,
) : Navigator {
  override fun goTo(screen: Screen) {
    when (screen) {
      is UrlScreen -> onOpenUrl(screen.url)
      else -> navigator.goTo(screen)
    }
  }

  override fun peek(): Screen? = navigator.peek()

  override fun peekBackStack(): List<Screen> = navigator.peekBackStack()

  override fun pop(result: PopResult?): Screen? = navigator.pop(result)

  override fun resetRoot(newRoot: Screen, saveState: Boolean, restoreState: Boolean): List<Screen> =
    navigator.resetRoot(newRoot, saveState, restoreState)
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { Navigator.NoOp }
