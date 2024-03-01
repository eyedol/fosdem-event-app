// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.api.dto.EventDto
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.createKtorEventsApiWithEvents
import com.addhen.fosdem.data.events.createKtorEventsApiWithLiveEvents
import com.addhen.fosdem.data.events.database.EventsDbDao
import com.addhen.fosdem.data.events.repository.mapper.toDay
import com.addhen.fosdem.data.events.repository.mapper.toEvent
import com.addhen.fosdem.data.events.repository.mapper.toRoom
import com.addhen.fosdem.data.events.repository.mapper.toTrack
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class EventsDataRepositoryTest : BaseDatabaseTest() {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var repository: EventsDataRepository
  private val fakeApi: FakeEventsApi = FakeEventsApi(coroutineTestRule.testDispatcherProvider)
  private lateinit var eventsDbDao: EventsDao

  @BeforeEach
  fun set() {
    eventsDbDao = EventsDbDao(database, coroutineTestRule.testDispatcherProvider)
    repository = EventsDataRepository(fakeApi, eventsDbDao)
  }

  @Test
  fun `getEvents should return events from database`() = coroutineTestRule.runTest {
    val date = LocalDate.parse("2023-02-04")
    val eventList = events.filter { it.day.date == date }.setDurationTime().toEvent()
    givenDaysAndEventsData(events)

    val result = repository.getEvents(date).first()

    assertEquals(true, result == eventList)
  }

  @Test
  fun `getEvent should return event from database`() = coroutineTestRule.runTest {
    val eventId = 1L
    val event = day1Event
    givenDaysAndEventsData(listOf(event))

    val result = repository.getEvent(eventId).first()

    assertTrue(result == event.setDurationTime().toEvent())
  }

  @Test
  fun `refresh should update data in the database`() = coroutineTestRule.runTest {
    // We need to limit the parallelism to 1 to avoid the test
    // failing due to the use of `withTimeout` in the refresh method
    withContext(Dispatchers.Default.limitedParallelism(1)) {
      repository.refresh()
    }

    val expected = eventsDbDao.getEvents().first()
    assertTrue(expected.isNotEmpty())
  }

  @Test
  fun `refresh get full xml schedule should update data in the databases`() =
    coroutineTestRule.runTest {
      // It's the actual size of all the events in the schedules.xml file
      val expectedScheduleSize = 108
      val schedulesService = FakeEventsApiWithSchedulesXml(coroutineTestRule.testDispatcherProvider)

      val sut = EventsDataRepository(schedulesService, eventsDbDao)

      // We need to limit the parallelism to 1 to avoid the test
      // failing due to the use of `withTimeout` in the refresh method
      withContext(Dispatchers.Default.limitedParallelism(1)) {
        sut.refresh()
      }

      val eventsDto = schedulesService.events
      val actual = eventsDbDao.getEvents().first()
      assertTrue(actual.isNotEmpty())
      assertEquals(expectedScheduleSize, actual.size)

      var linkIdCounter = 0L
      var attachmentIdCounter = 0L

      eventsDto.days.forEach { day ->
        day.rooms.forEach { room ->
          room.events.forEach { event ->
            // Calling setDurationTime to parse the duration time from the DTO otherwise the
            // assertion fails
            val actualEvent = actual.find { it.id == event.id }
            val expectedEventEntity = event.toEvent(day.toDay(), room.toRoom()).setDurationTime()
            val actualEventEntityWithRelatedData = expectedEventEntity.copy(
              links = expectedEventEntity.links.map {
                it.copy(id = ++linkIdCounter)
              },
              attachments = expectedEventEntity.attachments.map {
                it.copy(id = ++attachmentIdCounter)
              },
              room = expectedEventEntity.room.copy(id = actualEvent?.room?.id),
            )
            assertEquals(true, actualEvent != null)
            assertEquals(actualEventEntityWithRelatedData, actualEvent)
          }
        }
      }
    }

  @Test
  fun `refresh should handle API error`() = coroutineTestRule.runTest {
    fakeApi.setFailure(Throwable("Failed to add items to database"))
    val expected = eventsDbDao.getEvents().first()

    repository.refresh()

    assertTrue(expected.isEmpty())
  }

  @Test
  fun `toggleBookmark should successfully toggle events bookmark to true`() =
    coroutineTestRule.runTest {
      val eventId = 1L
      val event = day1Event.copy(isBookmarked = false)
      givenDaysAndEventsData(listOf(event))

      repository.toggleBookmark(eventId)

      val actual = repository.getEvent(eventId).first()
      assertTrue(actual.isBookmarked)
    }

  @Test
  fun `toggleBookmark should successfully toggle events bookmark to false`() =
    coroutineTestRule.runTest {
      val eventId = 1L
      val event = day1Event.copy(isBookmarked = true)
      givenDaysAndEventsData(listOf(event))

      repository.toggleBookmark(eventId)

      val actual = repository.getEvent(eventId).first()
      assertFalse(actual.isBookmarked)
    }

  @Test
  fun `getTracks should return distinct event tracks from database`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day2Event, day3Event)
    val expectedTrackList = listOf(day1Event, day3Event).map { it.track }.map { it.toTrack() }
    givenDaysAndEventsData(events)

    val actual = repository.getTracks().first()

    assertEquals(true, actual == expectedTrackList)
  }

  @Test
  fun `getTracks should return event tracks from database`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day3Event)
    val trackList = events.map { it.track }.map { it.toTrack() }
    givenDaysAndEventsData(events)

    val actual = repository.getTracks().first()

    assertEquals(true, actual == trackList)
  }

  @Test
  fun `getAllBookmarkedEvents should successfully load all bookmarked events`() =
    coroutineTestRule.runTest {
      val event = day1Event.copy(isBookmarked = true)
      val event2 = day2Event.copy(isBookmarked = true)
      val events = listOf(event, event2)
      // Calling setDurationTime to parse the duration time from the DTO otherwise the
      // assertion fails
      val expected = listOf(
        event.setDurationTime(),
        event2.setRoomId().setDurationTime(),
      ).map { it.toEvent() }
      givenDaysAndEventsData(events)

      val actual = repository.getAllBookmarkedEvents().first()

      assertEquals(expected, actual)
    }

  @Test
  fun `getAllBookmarkedEvents should successfully return empty bookmarked events`() =
    coroutineTestRule.runTest {
      val event = day1Event.copy(isBookmarked = false)
      val event2 = day2Event.copy(isBookmarked = false)
      val events = listOf(event, event2)
      givenDaysAndEventsData(events)

      val actual = repository.getAllBookmarkedEvents().first()

      assertEquals(true, actual.isEmpty())
    }

  private suspend fun givenDaysAndEventsData(inputEvents: List<EventEntity> = events) {
    eventsDbDao.addDays(listOf(day, day2))
    eventsDbDao.insert(inputEvents)
  }

  class FakeEventsApi(private val dispatchers: AppCoroutineDispatchers) : EventsApi {
    private var events: EventDto? = null
    private lateinit var error: Throwable

    fun setFailure(throwable: Throwable) {
      events = null
      this.error = throwable
    }

    override suspend fun fetchEvents(): EventDto {
      events = createKtorEventsApiWithEvents(dispatchers).fetchEvents()
      return events ?: throw error
    }
  }

  internal class FakeEventsApiWithSchedulesXml(
    private val dispatchers: AppCoroutineDispatchers,
  ) : EventsApi {

    lateinit var events: EventDto
    override suspend fun fetchEvents(): EventDto {
      events = createKtorEventsApiWithLiveEvents(dispatchers).fetchEvents()
      return events
    }
  }
}
