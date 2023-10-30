// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.screens

import com.slack.circuit.runtime.screen.Screen

@CommonParcelize
object SessionScreen : AppScreen(name = "Session()")

@CommonParcelize
data class SessionDetailScreen(val eventId: Long) : AppScreen(name = "SessionDetailScreen()") {
  override val arguments get() = mapOf("id" to eventId)
}

@CommonParcelize
data object SessionBookmarkScreen : AppScreen(name = "SessionBookmark()")

@CommonParcelize
data object SessionSearchScreen : AppScreen(name = "SessionSearch()")

@CommonParcelize
data object MapScreen : AppScreen(name = "Map()")

@CommonParcelize
data object AboutScreen : AppScreen(name = "About()")

@CommonParcelize
data class UrlScreen(val url: String) : AppScreen(name = "UrlScreen()") {
  override val arguments get() = mapOf("url" to url)
}

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
