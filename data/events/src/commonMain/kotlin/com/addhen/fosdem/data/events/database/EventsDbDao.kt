// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Events
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone

class EventsDbDao(
  private val clock: Clock,
  private val timeZone: TimeZone,
  private val appDatabase: Database,
  private val backgroundDispatcher: CoroutineDispatcher,
) : EventsDao {
  override fun getEvent(localDate: LocalDate): Flow<List<Events>> {
    TODO("Not yet implemented")
  }

  override fun getEvent(eventId: Long): Flow<Events> {
    TODO("Not yet implemented")
  }

  override fun toggleBookmark(eventId: Long) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteAll() {
    TODO("Not yet implemented")
  }

  override suspend fun insert(movies: List<Events>) {
    TODO("Not yet implemented")
  }
}
