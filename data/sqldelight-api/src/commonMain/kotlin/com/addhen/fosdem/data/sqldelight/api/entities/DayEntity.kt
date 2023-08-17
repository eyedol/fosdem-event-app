package com.addhen.fosdem.data.sqldelight.api.entities

import kotlinx.datetime.LocalDate

data class DayEntity(
  val id: Long,
  val date: LocalDate
)
