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
  val bookmarkTitle: String,
  val sessionContentDescription: String,
  val searchContentDescription: String,
  val aboutContentDescription: String,
  val mapContentDescription: String,
  val bookmarkContentDescription: String,
  val dayOneTitle: String,
  val dayTwoTitle: String,
  val readMoreLabel: String,
  val watchVideoLabel: String,
  val shareTitle: String,
  val roomTitle: String,
  val trackTitle: String,
  val dateTitle: String,
  val speakerTitle: String,
  val linkTitle: String,
  val attachmentTitle: String,
  val addToCalendarTitle: String,
  val addToBookmarksTitle: String,
  val removeFromBookmarksTitle: String,
  val bookmarkFilterAllTitle: String,
  val bookmarkedItemEmpty: String,
  val bookmarkedItemNotFound: String,
  val dayTitle: String,
  val aboutFosdem: String,
  val placeTitle: String,
  val placeLink: String,
  val placeDescription: String,
  val appVersion: String,
  val dateDescription: String,
  val licenseTitle: String,
  val privacyPolicyTitle: String,
  val searchNotFound: (String) -> String,
  val searchTermPlaceHolder: String,
  val sessionEmpty: String,
  val refresh: String,
  val dragHandleContentDescription: String,
)

object Locales {
  const val EN = "en"
}
