// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.screens

import com.slack.circuit.runtime.screen.Screen

@CommonParcelize
object SessionScreen : AppScreen(name = "Session")

@CommonParcelize
object SessionDetailScreen : AppScreen(name = "SessionDetail")

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
