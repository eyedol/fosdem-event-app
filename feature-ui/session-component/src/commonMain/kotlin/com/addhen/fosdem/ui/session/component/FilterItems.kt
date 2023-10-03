// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.runtime.Immutable
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.Track

@Immutable
data class FilterRoom(val id: Long, val name: String)

@Immutable
data class FilterTrack(val name: String)

fun Room.toFilterRoom() = FilterRoom(
  id,
  name,
)

fun Track.toFilterTrack() = FilterTrack(name)
