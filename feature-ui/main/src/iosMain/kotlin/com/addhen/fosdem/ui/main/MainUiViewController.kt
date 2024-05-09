// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIViewController

typealias MainUiViewController = () -> UIViewController

@Inject
@Suppress("ktlint:standard:function-naming")
fun MainUiViewController(
  mainContent: MainContent,
): UIViewController = ComposeUIViewController {
  val backstack = rememberSaveableBackStack(listOf(SessionsScreen))
  val navigator = rememberCircuitNavigator(backstack, onRootPop = { /* no-op */ })
  val uiViewController = LocalUIViewController.current

  mainContent.Content(
    backstack,
    navigator,
    { url ->
      val safari = SFSafariViewController(NSURL(string = url))
      uiViewController.presentViewController(safari, animated = true, completion = null)
    },
    { /* Implement sharing of event details */ },
    { _, _, _, _, _ ->
      /* Implement registering an event to app calendar */
    },
    Modifier,
  )
}
