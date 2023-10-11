// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.rooms.database.DatabaseTest
import com.addhen.fosdem.data.rooms.database.RoomsDbDao
import com.addhen.fosdem.data.rooms.repository.mapper.toRoom
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.test.CoroutineTestRule
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class RoomsDataRepositoryTest : DatabaseTest() {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var repository: RoomsDataRepository
  private lateinit var roomsDbDao: RoomsDao

  @BeforeEach
  fun set() {
    roomsDbDao = RoomsDbDao(
      database,
      coroutineTestRule.testDispatcherProvider,
    )
    repository = RoomsDataRepository(roomsDbDao)
  }

  @Test
  fun `getRooms should successfully return rooms from database`() = coroutineTestRule.runTest {
    val room = RoomEntity(1, "room1")
    database.roomsQueries.insert(room.id, room.name)

    val result = repository.getRooms().first()

    assertEquals(true, result is AppResult.Success)
    assertEquals(true, (result as AppResult.Success).data == listOf(room.toRoom()))
  }
}
