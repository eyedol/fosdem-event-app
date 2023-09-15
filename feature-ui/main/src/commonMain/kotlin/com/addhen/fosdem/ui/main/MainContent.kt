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
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias MainContent = @Composable (
  backstack: SaveableBackStack,
  navigator: Navigator,
  modifier: Modifier,
) -> Unit

@Inject
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainContent(
  @Assisted backstack: SaveableBackStack,
  @Assisted navigator: Navigator,
  circuitConfig: Circuit,
  @Assisted modifier: Modifier = Modifier,
) {
  val appNavigator: Navigator = remember(navigator) {
    AppNavigator(navigator)
  }

  ProvideStrings {
    CompositionLocalProvider(
      LocalNavigator provides appNavigator,
      LocalWindowSizeClass provides calculateWindowSizeClass(),
    ) {
      CircuitCompositionLocals(circuitConfig) {
        AppTheme(
          useDarkColors = false,
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
) : Navigator {
  override fun goTo(screen: Screen) = navigator.goTo(screen)

  override fun pop(): Screen? = navigator.pop()

  override fun resetRoot(newRoot: Screen): List<Screen> = navigator.resetRoot(newRoot)
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { Navigator.NoOp }
