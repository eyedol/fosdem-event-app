// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack

data class SearchFilters(
  val days: List<DayTab> = emptyList(),
  val tracks: List<FilterTrack> = emptyList(),
  val rooms: List<FilterRoom> = emptyList(),
  val searchQuery: String = "",
)
