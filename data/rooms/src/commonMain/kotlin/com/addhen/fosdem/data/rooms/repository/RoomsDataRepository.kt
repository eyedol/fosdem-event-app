// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.repository

import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.data.rooms.repository.mapper.toRooms
import com.addhen.fosdem.model.api.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class RoomsDataRepository(
  private val database: RoomsDao,
) : RoomsRepository {

  override fun getRooms(): Flow<List<Room>> = database
    .getRooms()
    .map { it.toRooms() }
}
