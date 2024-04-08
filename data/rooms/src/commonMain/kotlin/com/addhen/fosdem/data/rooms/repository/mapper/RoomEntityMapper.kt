// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.repository.mapper

import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.model.api.Room

internal fun RoomEntity.toRoom() = Room(
  id = id ?: 0L,
  name = name,
)

internal fun List<RoomEntity>.toRooms() = map { it.toRoom() }
