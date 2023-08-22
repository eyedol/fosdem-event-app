// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.model.api.Event
import kotlinx.datetime.LocalDate

interface EventsRepository {

  suspend fun getEvents(date: LocalDate): List<Event>

  suspend fun getEvent(id: Long): Event

  suspend fun fetchAndSaveEvent()
}
