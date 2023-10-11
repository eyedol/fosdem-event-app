// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.api.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.model.api.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {

  fun getRooms(): Flow<AppResult<List<Room>>>
}
