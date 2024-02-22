// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.i18n

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN, default = true)
val EnAppStrings = AppStrings(
  appName = "FOSDEM",
  sessionTitle = "Sessions",
  mapTitle = "Map",
  searchTitle = "Search",
  aboutTitle = "About",
  bookmarkTitle = "Bookmark",
  aboutContentDescription = "",
  searchContentDescription = "",
  sessionContentDescription = "",
  mapContentDescription = "",
  bookmarkContentDescription = "",
  dayOneTitle = "Saturday",
  dayTwoTitle = "Sunday",
  readMoreLabel = "Read more",
  watchVideoLabel = "Watch Video",
  shareTitle = "Share",
  roomTitle = "Room",
  trackTitle = "Track",
  dateTitle = "Date",
  speakerTitle = "Speaker",
  linkTitle = "Link",
  attachmentTitle = "Attachment",
  addToCalendarTitle = "Add to calendar",
  addToBookmarksTitle = "Add to bookmarks",
  removeFromBookmarksTitle = "Remove from bookmarks",
  bookmarkedItemEmpty = "No sessions bookmarked",
  bookmarkedItemNotFound = "Add the sessions you are interested in to your bookmarks\n to see them",
  dayTitle = "Day",
  aboutFosdem = "FOSDEM is a two-day event organised by volunteers to promote the widespread use " +
    "of free and open source software.\n\nUsually taking place in the beautiful city of " +
    "Brussels (Belgium), FOSDEM is widely recognised as the best such conference in Europe.",
  placeTitle = "Place",
  placeDescription = "ULB Solbosch Campus, Brussels, Belgium",
  placeLink = "OpenStreetMap",
  appVersion = "App Version",
  dateDescription = "03.02.2024(Saturday) - 04.02.2024(Sunday) 2 days",
  licenseTitle = "License",
  privacyPolicyTitle = "Privacy Policy",
  searchNotFound = { searchTerm -> "Nothing matched your search criteria \"$searchTerm\"" },
  searchTermPlaceHolder = "Enter a session, a speaker or a term",
  sessionEmpty = "No session loaded",
  refresh = "Refresh",
  dragHandleContentDescription = "Drag Handle",
  openSourceLicenses = "Open Source Licenses",
  tryAgain = "Try again",
)
