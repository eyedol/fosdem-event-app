// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.model.api.Event
import kotlinx.datetime.LocalDate

class EventsDataRepository(
  private val api: EventsApi,
  private val database: EventsDao,
  private val appDatabase: Database,
) : EventsRepository {
  override suspend fun getEvents(date: LocalDate): List<Event> {
    TODO("Not yet implemented")
  }

  override suspend fun getEvent(id: Long): Event {
    TODO("Not yet implemented")
  }

  override suspend fun fetchAndSaveEvent() {
    TODO("Not yet implemented")
  }

  override suspend fun deleteAll() {
    TODO("Not yet implemented")
  }

  override suspend fun refresh() {
    val events = api.fetchEvents()
    database.deleteAll()
  }
}
