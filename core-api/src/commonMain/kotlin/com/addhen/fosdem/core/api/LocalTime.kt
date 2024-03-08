package com.addhen.fosdem.core.api

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime

operator fun LocalTime.plus(to: LocalTime): LocalTime {
  val minutes = (this.hour * 60 + this.minute) + (to.hour * 60 + to.minute)
  val hours = minutes / 60 % 24
  val minutesRemaining = minutes % 60
  return LocalTime(hours, minutesRemaining)
}

operator fun LocalTime.minus(other: LocalTime): LocalTime {
  val minutes1 = this.hour * 60 + this.minute
  val minutes2 = other.hour * 60 + other.minute
  val difference = minutes1 - minutes2

  val hours = if (difference >= 0) {
    difference / 60
  } else {
    // Handle negative difference (assuming earlier time is subtracted from later time)
    (difference + 24 * 60) / 60 // Adjust for wrapping around midnight
  }

  val minutesRemaining = difference % 60

  return LocalTime(hours, minutesRemaining)
}

fun LocalTime.toLocalDateTime(date: LocalDate) = date.atTime(this)
