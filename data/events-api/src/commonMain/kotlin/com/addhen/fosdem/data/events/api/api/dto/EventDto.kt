package com.addhen.fosdem.data.events.api.api.dto

import com.addhen.fosdem.model.api.Event
import kotlinx.datetime.LocalDate

data class EventDto(val days: List<Day>) {

  data class Day(val date: LocalDate, val events: List<Event> = emptyList())
}
