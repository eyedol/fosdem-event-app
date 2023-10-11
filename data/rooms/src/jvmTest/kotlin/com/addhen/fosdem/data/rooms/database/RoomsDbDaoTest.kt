// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.database

import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.test.CoroutineTestRule
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class RoomsDbDaoTest : DatabaseTest() {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: RoomsDao

  @BeforeEach
  fun set() {
    sut = RoomsDbDao(database, coroutineTestRule.testDispatcherProvider)
  }

  @Test
  fun `successfully gets events by date one from the database`() = coroutineTestRule.runTest {
    // Seed some data
    val room = RoomEntity(1, "room1")
    database.roomsQueries.insert(room.id, room.name)

    val actual = sut.getRooms().first()

    assertEquals(1, actual.size)
    assertEquals(listOf(room), actual)
  }
}
