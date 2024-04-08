// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.common

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SessionFilters(
  val days: ImmutableList<DayTab> = listOf<DayTab>().toImmutableList(),
  val tracks: ImmutableList<FilterTrack> = listOf<FilterTrack>().toImmutableList(),
  val rooms: ImmutableList<FilterRoom> = listOf<FilterRoom>().toImmutableList(),
  val searchQuery: String = "",
) {

  companion object {

    val Saver: Saver<SessionFilters, *> = listSaver(
      save = {
        listOf(it.days.toList(), it.searchQuery, it.rooms.toList(), it.tracks.toList())
      },

      restore = {
        @Suppress("UNCHECKED_CAST")
        (
          SessionFilters(
            days = (it[0] as List<DayTab>).toImmutableList(),
            searchQuery = it[1].toString(),
            rooms = (it[2] as List<FilterRoom>).toImmutableList(),
            tracks = (it[3] as List<FilterTrack>).toImmutableList(),
          )
          )
      },
    )
  }
}
