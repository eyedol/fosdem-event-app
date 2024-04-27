// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.test.fake.event

import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import java.util.concurrent.atomic.AtomicBoolean

class FakeEventsRepository : EventsRepository {

  private val events = mutableListOf<Event>()

  private val tracks = mutableListOf<Track>()
  val shouldCauseAnError: AtomicBoolean = AtomicBoolean(false)
  override fun getEvents(): Flow<List<Event>> = flow { emit(events) }

  override fun getEvents(date: LocalDate): Flow<List<Event>> = flow {
    val event = events.filter { it.day.date == date }
    emit(event)
  }

  override fun getAllBookmarkedEvents(): Flow<List<Event>> = flow {
    emit(events.filter { it.isBookmarked })
  }

  override fun getEvent(id: Long): Flow<Event> = flow {
    if (shouldCauseAnError.get()) {
      throw RuntimeException("Error occurred while getting event with id: $id")
    }
    emit(events.first { it.id == id })
  }

  override fun getTracks(): Flow<List<Track>> = flow { emit(tracks) }

  override suspend fun toggleBookmark(id: Long): Result<Unit> {
    if (shouldCauseAnError.get()) {
      shouldCauseAnError.set(false)
      return Result.failure(RuntimeException("Error occurred while toggling bookmark"))
    }
    val event = events.first { it.id == id }
    events.replaceAll { event.copy(isBookmarked = event.isBookmarked.not()) }
    return Result.success(Unit)
  }

  override suspend fun refresh(): Result<Unit> = Result.success(Unit)

  fun addEvents(vararg newEvents: Event) = events.addAll(newEvents)

  fun clearEvents() = events.clear()

  fun events(): List<Event> = events.toList()
}
