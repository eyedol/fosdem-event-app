// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.repository

import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.rooms.database.RoomsDbDao
import com.addhen.fosdem.data.rooms.repository.mapper.toRoom
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.database.BaseDatabaseTest
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class RoomsDataRepositoryTest : BaseDatabaseTest() {

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

    val actual = repository.getRooms().first()

    assertEquals(true, actual == listOf(room.toRoom()))
  }
}
