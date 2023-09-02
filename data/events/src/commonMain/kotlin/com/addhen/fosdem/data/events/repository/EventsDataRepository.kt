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
import com.addhen.fosdem.model.api.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class EventsDataRepository(
  private val api: EventsApi,
  private val database: EventsDao,
) : EventsRepository {
  override suspend fun getEvents(date: LocalDate): Flow<List<Event>> = database.getEvents(date).map { it.toEvent() }

  override suspend fun getEvent(id: Long): Flow<Event> = database.getEvent(id).map { it.toEvent() }

  override suspend fun deleteAll() = database.deleteAll()

  override suspend fun refresh() {
    val eventDto = api.fetchEvents()
    if(eventDto.days.isEmpty()) return
    database.deleteAll()
    database.addDays(eventDto.days.toDays())
    eventDto.days.forEach { day ->
      day.rooms.forEach {room ->
        database.insert(room.events.toEvents(day.toDay(), room.toRoom()))
      }
    }
  }
}
