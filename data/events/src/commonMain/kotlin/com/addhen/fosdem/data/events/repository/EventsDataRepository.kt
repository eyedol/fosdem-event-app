// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.events.repository.mapper.toDay
import com.addhen.fosdem.data.events.repository.mapper.toDays
import com.addhen.fosdem.data.events.repository.mapper.toEvent
import com.addhen.fosdem.data.events.repository.mapper.toEvents
import com.addhen.fosdem.data.events.repository.mapper.toRoom
import com.addhen.fosdem.data.events.repository.mapper.toTrack
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Track
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeout
import kotlinx.datetime.LocalDate
import me.tatarka.inject.annotations.Inject

@Inject
class EventsDataRepository(
  private val api: EventsApi,
  private val database: EventsDao,
) : EventsRepository {

  override fun getTracks(): Flow<List<Track>> = database
    .getTracks()
    .map { it.map { trackEntity -> trackEntity.toTrack() } }

  override fun getEvents(): Flow<List<Event>> {
    return database
      .getEvents()
      .map { it.toEvent() }
  }

  override fun getAllBookmarkedEvents(): Flow<List<Event>> = database
    .getAllBookmarkedEvents()
    .map { it.toEvent() }

  override fun getEvents(date: LocalDate): Flow<List<Event>> = database
    .getEvents(date)
    .map { it.toEvent() }

  override fun getEvent(
    id: Long,
  ): Flow<Event> = database
    .getEvent(id)
    .map { it.toEvent() }

  override suspend fun deleteAll() = database.deleteAll()

  override suspend fun refresh() = runCatching {
    withTimeout(5.minutes) {
      refreshEvents()
    }
  }

  private suspend fun refreshEvents() {
    val eventDto = api.fetchEvents()
    database.deleteAll()
    database.addDays(eventDto.days.toDays())
    eventDto.days.forEach { day ->
      day.rooms.forEach { room ->
        database.insert(room.events.toEvents(day.toDay(), room.toRoom()))
      }
    }
  }

  override suspend fun toggleBookmark(id: Long) = runCatching {
    database.toggleBookmark(id)
  }
}
