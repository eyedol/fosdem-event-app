// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.core.api.onSuccess
import com.addhen.fosdem.data.core.api.toAppError
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.cancellation.CancellationException

@Inject
class EventsDataRepository(
  private val api: EventsApi,
  private val database: EventsDao,
) : EventsRepository {

  override suspend fun getTracks(): AppResult<List<Track>> {
    return try {
      val tracks = database
        .getTracks()
        .map { it.toTrack() }
      AppResult.Success(tracks)
    } catch (e: Throwable) {
      if (e is CancellationException) {
        throw e
      }
      AppResult.Error(e.toAppError())
    }
  }

  override fun getEvents(): Flow<AppResult<List<Event>>> = database
    .getEvents()
    .map { AppResult.Success(it.toEvent()) }
    .catch { AppResult.Error(it.toAppError()) }

  override fun getEvent(
    id: Long,
  ): Flow<AppResult<Event>> = database
    .getEvent(id)
    .map { AppResult.Success(it.toEvent()) }
    .catch { AppResult.Error(it.toAppError()) }

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
