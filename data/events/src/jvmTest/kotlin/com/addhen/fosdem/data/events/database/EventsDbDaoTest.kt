// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.model.api.plusMinutes
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.database.BaseDatabaseTest
import com.addhen.fosdem.test.day
import com.addhen.fosdem.test.day1Event
import com.addhen.fosdem.test.day2
import com.addhen.fosdem.test.day2Event
import com.addhen.fosdem.test.day3Event
import com.addhen.fosdem.test.events
import com.addhen.fosdem.test.setDurationTime
import com.addhen.fosdem.test.setRoomId
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class EventsDbDaoTest : BaseDatabaseTest() {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: EventsDao

  @BeforeEach
  fun set() {
    sut = EventsDbDao(database, coroutineTestRule.testDispatcherProvider)
  }

  @Test
  fun `successfully gets events by date one from the database`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    val actual = sut.getEvents(day.date).first()

    assertEquals(1, actual.size)
    val expectedEvent = events.first()
    assertEquals(listOf(expectedEvent.setDurationTime()), actual)
  }

  @Test
  fun `successfully gets events by day two from the database`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    val actual = sut.getEvents(day2.date).first()

    assertEquals(2, actual.size)

    val expectedDay2Event1 = day2Event.setDurationTime().setRoomId()
    val expectedDay2Event2 = day3Event.setDurationTime()
    assertEquals(listOf(expectedDay2Event2, expectedDay2Event1), actual)
  }

  @Test
  fun `successfully gets events by id`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    val actual = sut.getEvent(3).first()

    val expectedEvent = events.last()
    assertEquals(
      expectedEvent.copy(
        duration = expectedEvent.start_time.plusMinutes(expectedEvent.duration),
      ),
      actual,
    )
  }

  @Test
  fun `successfully toggles event isBookmarked field to true`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    sut.toggleBookmark(3)
    val actual = sut.getEvent(3).first()

    assertEquals(true, actual.isBookmarked)
  }

  @Test
  fun `successfully toggles event isBookmarked field to false`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    sut.toggleBookmark(3) // Initial toggle state is false, this call toggles it to true
    sut.toggleBookmark(3) // Now toggle it back to false
    val actual = sut.getEvent(3).first()

    assertEquals(false, actual.isBookmarked)
  }

  @Test
  fun `successfully deletes all events related data`() = coroutineTestRule.runTest {
    // Seed some data
    givenDaysAndEventsData()

    sut.deleteRelatedData()

    val actual = sut.getEvents(day.date).first()
    val days = sut.getDays()

    //assertEquals(true, actual.isEmpty())
    assertEquals(true, actual.isNotEmpty())
    assertEquals(true, days.isNotEmpty())
    assertEvents(actual)
  }

  @Test
  fun `successfully gets all bookmarked events`() = runTest {
    // Seed some data
    val event = day1Event.copy(isBookmarked = true)
    val event2 = day2Event.copy(isBookmarked = true)
    val inputEvents = listOf(event, event2)
    val expected = listOf(
      event,
      event2.setRoomId(), // Change event2 room id to 1 as room name is unique
    ).setDurationTime()
    givenDaysAndEventsData(inputEvents)

    val actual = sut.getAllBookmarkedEvents().first()
    assertEquals(expected, actual)
  }

  @Test
  fun `successfully gets an empty bookmarked events`() = coroutineTestRule.runTest {
    // Seed some data
    val event = day1Event.copy(isBookmarked = false)
    val event2 = day2Event.copy(isBookmarked = false)
    val events = listOf(event, event2)
    givenDaysAndEventsData(events)

    val actual = sut.getAllBookmarkedEvents().first()

    assertEquals(true, actual.isEmpty())
  }

  @Test
  fun `successfully adds days and gets all the added days`() = coroutineTestRule.runTest {
    val date1 = "2023-02-16"
    val date2 = "2023-02-17"
    val days = listOf(
      DayEntity(1, LocalDate.parse(date1)),
      DayEntity(2, LocalDate.parse(date2)),
    )

    sut.addDays(days)
    val actual = sut.getDays()

    assertEquals(days, actual)
  }

  private suspend fun givenDaysAndEventsData(inputEvents: List<EventEntity> = events) {
    sut.addDays(listOf(day, day2))
    sut.insert(inputEvents)
  }

  private fun assertEvents(actual: List<EventEntity>) {
    for (event in actual) {
      assertEquals(true, event.links.isEmpty())
      assertEquals(true, event.attachments.isEmpty())
      assertEquals(true, event.speakers.isEmpty())
    }
  }
}
