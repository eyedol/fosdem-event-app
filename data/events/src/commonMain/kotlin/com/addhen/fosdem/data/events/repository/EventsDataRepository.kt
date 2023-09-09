// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.core.api.onSuccess
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.events.repository.mapper.toDay
import com.addhen.fosdem.data.events.repository.mapper.toDays
import com.addhen.fosdem.data.events.repository.mapper.toEvent
import com.addhen.fosdem.data.events.repository.mapper.toEvents
import com.addhen.fosdem.data.events.repository.mapper.toRoom
import com.addhen.fosdem.model.api.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import me.tatarka.inject.annotations.Inject

@Inject
class EventsDataRepository(
  private val api: EventsApi,
  private val database: EventsDao,
) : EventsRepository {
  override suspend fun getEvents(
    date: LocalDate,
  ): Flow<AppResult<List<Event>>> = database
    .getEvents(date)
    .map { AppResult.Success(it.toEvent()) }

  override suspend fun getEvent(
    id: Long,
  ): Flow<AppResult<Event>> = database
    .getEvent(id)
    .map { AppResult.Success(it.toEvent()) }

  override suspend fun deleteAll() = database.deleteAll()

  override suspend fun refresh() {
    api.fetchEvents()
      .onSuccess { eventDto ->
        database.deleteAll()
        database.addDays(eventDto.days.toDays())
        eventDto.days.forEach { day ->
          day.rooms.forEach { room ->
            database.insert(room.events.toEvents(day.toDay(), room.toRoom()))
          }
        }
      }
  }

  override suspend fun toggleBookmark(id: Long) = database.toggleBookmark(id)
}
