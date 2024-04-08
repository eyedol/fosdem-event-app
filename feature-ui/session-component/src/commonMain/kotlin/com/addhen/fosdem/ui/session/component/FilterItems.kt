// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.Track

@Immutable
data class FilterRoom(val id: Long, val name: String) {

  companion object {
    val Saver: Saver<FilterRoom, Any> = listSaver(
      save = { listOf(it.id.toString(), it.name) },
      restore = {
        FilterRoom(
          id = it.first().toLong(),
          name = it.last().toString(),
        )
      },
    )
  }
}

@Immutable
data class FilterTrack(val name: String, val type: String) {
  companion object {
    val Saver: Saver<FilterTrack, Any> = listSaver(
      save = { listOf(it.name, it.type) },
      restore = {
        FilterTrack(
          name = it.first().toString(),
          type = it.last().toString(),
        )
      },
    )
  }
}

fun Room.toFilterRoom() = FilterRoom(
  id,
  name,
)

fun Track.toFilterTrack() = FilterTrack(name, type)
