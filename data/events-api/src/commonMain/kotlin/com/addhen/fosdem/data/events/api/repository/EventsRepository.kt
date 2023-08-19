/*
 * Copyright 2022 Addhen Limited
 */
package com.addhen.fosdem.data.events.api.repository

import com.addhen.fosdem.model.api.Event
import kotlinx.datetime.LocalDate

interface EventsRepository {

  suspend fun getEvents(date: LocalDate): List<Event>

  suspend fun getEvent(id: Long): Event

  suspend fun fetchAndSaveEvent()
}
