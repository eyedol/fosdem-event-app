package com.addhen.fosdem.data.events.api.api

import com.addhen.fosdem.data.events.api.api.dto.EventDto

interface EventsApi {
  suspend fun fetchEvents(): EventDto
}
