// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsRepository {

  fun getEvents(): Flow<AppResult<List<Event>>>

  fun getEvents(date: LocalDate): Flow<AppResult<List<Event>>>

  fun getEvent(id: Long): Flow<AppResult<Event>>

  fun getTracks(): Flow<AppResult<List<Track>>>

  suspend fun toggleBookmark(id: Long)

  suspend fun deleteAll()

  suspend fun refresh()
}
