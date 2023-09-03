// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.api

import com.addhen.fosdem.data.events.api.api.dto.EventDto
import com.addhen.fosdem.data.core.api.AppResult

interface EventsApi {
  suspend fun fetchEvents(): AppResult<EventDto>
}
