// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.api.database

import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import kotlinx.coroutines.flow.Flow

interface RoomsDao {
  fun getRooms(): Flow<List<RoomEntity>>
}
