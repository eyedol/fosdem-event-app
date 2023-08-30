// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.database

import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsDao {

  fun getEvents(date: LocalDate): Flow<List<EventEntity>>

  fun getEvent(eventId: Long): Flow<EventEntity>

  suspend fun toggleBookmark(eventId: Long)

  suspend fun deleteAll()

  suspend fun insert(events: List<EventEntity>)
}
