// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.core.api.network.ApiService
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.api.dto.EventDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone

class KtorEventsApi(
  private val clock: Clock,
  private val timeZone: TimeZone,
  private val api: ApiService,
) : EventsApi {
  override suspend fun fetchEvents(): EventDto {
    TODO("Not yet implemented")
  }
}
