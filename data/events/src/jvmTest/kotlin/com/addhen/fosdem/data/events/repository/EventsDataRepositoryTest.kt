// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.core.api.AppError
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.core.api.onSuccess
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.api.dto.EventDto
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.createKtorEventsApiWithEvents
import com.addhen.fosdem.data.events.day1Event
import com.addhen.fosdem.data.events.day2Event
import com.addhen.fosdem.data.events.events
import com.addhen.fosdem.data.events.repository.mapper.toDay
import com.addhen.fosdem.data.events.repository.mapper.toEvent
import com.addhen.fosdem.data.events.repository.mapper.toEvents
import com.addhen.fosdem.data.events.repository.mapper.toRoom
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity
import com.addhen.fosdem.test.CoroutineTestRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class EventsDataRepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var repository: EventsDataRepository
  private val fakeApi: FakeEventsApi = FakeEventsApi(coroutineTestRule.testDispatcherProvider)
  private val fakeDatabase: FakeEventsDao = FakeEventsDao()

  @BeforeEach
  fun setUp() {
    repository = EventsDataRepository(fakeApi, fakeDatabase)
  }

  @Test
  fun `getEvents should return events from database`() = coroutineTestRule.runTest {
    val date = LocalDate.parse("2023-02-04")
    val eventList = events.toEvent()
    fakeDatabase.addEvents(date, events)

    val result = repository.getEvents(date).first()

    assertEquals(true, result is AppResult.Success)
    assertEquals(true, (result as AppResult.Success).data == eventList)
  }

  @Test
  fun `getEvent should return event from database`() = coroutineTestRule.runTest {
    val eventId = 1L
    val event = day1Event
    fakeDatabase.addEvent(eventId, event)

    val result = repository.getEvent(eventId).first()

    assertTrue(result is AppResult.Success)
    assertTrue((result as AppResult.Success).data == event.toEvent())
  }

  @Test
  fun `deleteAll should clear the data from database`() = coroutineTestRule.runTest {
    fakeDatabase.addEvent(1L, day1Event)
    fakeDatabase.addEvent(2L, day2Event)

    repository.deleteAll()

    assertTrue(fakeDatabase.isEmpty())
  }

  @Test
  fun `refresh should update data in the database`() = coroutineTestRule.runTest {
    val eventDto = (
      createKtorEventsApiWithEvents(coroutineTestRule.testDispatcherProvider)
        .fetchEvents() as AppResult.Success
      ).data
    fakeApi.setEvents(eventDto)

    repository.refresh()

    // Verify that the fake database contains the expected data
    for (day in eventDto.days) {
      for (room in day.rooms) {
        val events = room.events.toEvents(day.toDay(), room.toRoom())
        assertTrue(fakeDatabase.containsEvents(events))
      }
    }
  }

  @Test
  fun `refresh should handle API error`() = coroutineTestRule.runTest {
    fakeApi.setFailure(Throwable("Failed to add items to database"))

    repository.refresh()

    assertTrue(fakeDatabase.isEmpty())
  }

  @Test
  fun `toggleBookmark should successfully toggle events bookmark to true`() =
    coroutineTestRule.runTest {
      val eventId = 1L
      val event = day1Event.copy(isBookmarked = false)
      fakeDatabase.addEvent(eventId, event)

      repository.toggleBookmark(eventId)

      repository.getEvent(eventId).first()
        .onSuccess { assertTrue(it.isBookmarked) }
    }

  @Test
  fun `toggleBookmark should successfully toggle events bookmark to false`() =
    coroutineTestRule.runTest {
      val eventId = 1L
      val event = day1Event.copy(isBookmarked = true)
      fakeDatabase.addEvent(eventId, event)

      repository.toggleBookmark(eventId)

      repository.getEvent(eventId).first()
        .onSuccess { assertFalse(it.isBookmarked) }
    }

  class FakeEventsApi(private val dispatchers: AppCoroutineDispatchers) : EventsApi {
    private var events: AppResult<EventDto>? = null

    fun setEvents(eventDto: EventDto) {
      events = AppResult.Success(eventDto)
    }

    fun setFailure(error: Throwable) {
      events = AppResult.Error(AppError.ApiException.NetworkException(error))
    }

    override suspend fun fetchEvents(): AppResult<EventDto> {
      if (events != null && events is AppResult.Error) return events as AppResult.Error
      events = createKtorEventsApiWithEvents(dispatchers).fetchEvents()
      return events ?: AppResult.Error(
        AppError.ApiException.NetworkException(Throwable("No events set in FakeEventsApi")),
      )
    }
  }

  class FakeEventsDao : EventsDao {
    private val eventsMap: MutableMap<Long, EventEntity> = mutableMapOf()
    private val eventsListMap: MutableMap<LocalDate, List<EventEntity>> = mutableMapOf()
    private val daysList: MutableList<DayEntity> = mutableListOf()

    fun addEvent(eventId: Long, event: EventEntity) {
      eventsMap[eventId] = event
    }

    fun addEvents(date: LocalDate, events: List<EventEntity>) {
      eventsListMap[date] = events
    }

    fun isEmpty(): Boolean {
      return eventsMap.isEmpty() && eventsListMap.isEmpty()
    }

    fun containsEvents(events: List<EventEntity>): Boolean {
      return events.all { event -> eventsMap.containsValue(event) }
    }

    override fun getEvent(eventId: Long): Flow<EventEntity> {
      return flowOf(eventsMap[eventId]).filterNotNull()
    }

    override fun getEvents(date: LocalDate): Flow<List<EventEntity>> {
      return flowOf(eventsListMap[date]).filterNotNull()
    }

    override fun getEvents(): Flow<List<EventEntity>> {
      return flowOf(eventsListMap.values.flatten()).filterNotNull()
    }

    override fun getTracks(): Flow<List<TrackEntity>> {
      return flowOf(
        listOf(TrackEntity("name1", "Track1"), TrackEntity("name2", "Track2")),
      )
    }

    override suspend fun toggleBookmark(eventId: Long) {
      val event = eventsMap[eventId] ?: return
      eventsMap[eventId] = event.copy(isBookmarked = !event.isBookmarked)
    }

    override suspend fun deleteAll() {
      eventsMap.clear()
      eventsListMap.clear()
      daysList.clear()
    }

    override suspend fun insert(events: List<EventEntity>) {
      events.forEach { eventsMap[it.id] = it }
    }

    override suspend fun addDays(days: List<DayEntity>) {
      daysList.addAll(days)
    }

    override suspend fun getDays(): List<DayEntity> {
      return daysList
    }
  }
}
