// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject

@Inject
class RoomsDbDao(
  private val appDatabase: Database,
  private val backgroundDispatcher: AppCoroutineDispatchers,
) : RoomsDao {

  override fun getRooms(): Flow<List<RoomEntity>> {
    return appDatabase.roomsQueries
      .selectAll(roomQueriesMapper)
      .asFlow()
      .mapToList(backgroundDispatcher.io)
      .flowOn(backgroundDispatcher.io)
  }

  private val roomQueriesMapper = {
      id: Long,
      name: String?,
    ->
    RoomEntity(
      id = id,
      name = name ?: ""
    )
  }
}
