// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.screens

import com.slack.circuit.runtime.screen.Screen

@CommonParcelize
data object SessionsScreen : AppScreen(name = "Sessions()")

@CommonParcelize
data class SessionDetailScreen(val eventId: Long) : AppScreen(name = "SessionDetail()") {
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
data object LicensesScreen : AppScreen(name = "Licenses()")

@CommonParcelize
data class UrlScreen(val url: String) : AppScreen(name = "UrlScreen()") {
  override val arguments get() = mapOf("url" to url)
}

@CommonParcelize
data class ShareScreen(val info: String) : AppScreen(name = "ShareScreen()") {
  override val arguments get() = mapOf("info" to info)
}

@CommonParcelize
data class CalendarScreen(
  val title: String,
  val room: String,
  val description: String,
  val startAtMillSeconds: Long,
  val endAtMillSeconds: Long,
) : AppScreen(name = "CalendarScreen()") {
  override val arguments get() = mapOf(
    "title" to title,
    "room" to room,
    "description" to description,
    "startAtMillSeconds" to startAtMillSeconds.toString(),
    "endAtMillSeconds" to endAtMillSeconds.toString(),
  )
}

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
