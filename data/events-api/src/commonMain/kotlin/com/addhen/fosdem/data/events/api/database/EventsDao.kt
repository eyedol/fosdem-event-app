// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.database

import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsDao {

  fun getEvents(date: LocalDate): Flow<List<EventEntity>>

  fun getEvents(): Flow<List<EventEntity>>

  fun getAllBookmarkedEvents(): Flow<List<EventEntity>>

  fun getEvent(eventId: Long): Flow<EventEntity>

  fun getTracks(): Flow<List<TrackEntity>>

  suspend fun toggleBookmark(eventId: Long)

  suspend fun deleteRelatedData()

  suspend fun insert(events: List<EventEntity>)

  suspend fun addDays(days: List<DayEntity>)

  suspend fun getDays(): List<DayEntity>
}
