// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.repository

import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsRepository {

  fun getEvents(): Flow<List<Event>>

  fun getAllBookmarkedEvents(): Flow<List<Event>>

  fun getEvents(date: LocalDate): Flow<List<Event>>

  fun getEvent(id: Long): Flow<Event>

  fun getTracks(): Flow<List<Track>>

  suspend fun toggleBookmark(id: Long): Result<Unit>

  suspend fun refresh(): Result<Unit>
}
