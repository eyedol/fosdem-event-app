// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.core.api.network.ApiService
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.api.dto.EventDto

class KtorEventsApi(
  private val api: ApiService,
) : EventsApi {
  override suspend fun fetchEvents(): EventDto {
    return api.get<EventDto>()
  }
}
