// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.i18n

val Strings: Map<String, AppStrings> = mapOf(
  "en" to EnAppStrings,
)

data class AppStrings(
  val sessionTitle: String,
  val searchTitle: String,
  val aboutTitle: String,
  val mapTitle: String,
  val sessionContentDescription: String,
  val searchContentDescription: String,
  val aboutContentDescription: String,
  val mapContentDescription: String,
)

object Locales {
  const val EN = "en"
}
