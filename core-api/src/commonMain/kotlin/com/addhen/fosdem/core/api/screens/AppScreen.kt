// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.screens

import com.slack.circuit.runtime.screen.Screen

@Parcelize
data object SessionsScreen : AppScreen(name = "Sessions()")

@Parcelize
data class SessionDetailScreen(val eventId: Long) : AppScreen(name = "SessionDetail()") {
  override val arguments get() = mapOf("id" to eventId)
}

@Parcelize
data object SessionBookmarkScreen : AppScreen(name = "SessionBookmark()")

@Parcelize
data object SessionSearchScreen : AppScreen(name = "SessionSearch()")

@Parcelize
data object MapScreen : AppScreen(name = "Map()")

@Parcelize
data object AboutScreen : AppScreen(name = "About()")

@Parcelize
data object LicensesScreen : AppScreen(name = "Licenses()")

@Parcelize
data class UrlScreen(val url: String) : AppScreen(name = "UrlScreen()") {
  override val arguments get() = mapOf("url" to url)
}

@Parcelize
data class ShareScreen(val info: String) : AppScreen(name = "ShareScreen()") {
  override val arguments get() = mapOf("info" to info)
}

@Parcelize
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
