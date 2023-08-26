// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.database

import com.addhen.fosdem.data.sqldelight.api.Events
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventsDao {

  fun getEvents(dayId: Long): Flow<List<EventEntity>>

  fun getEvent(eventId: Long): Flow<EventEntity>

  fun toggleBookmark(eventId: Long)

  suspend fun deleteAll()

  suspend fun insert(movies: List<Events>)
}
