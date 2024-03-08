package com.addhen.fosdem.core.api

import kotlinx.datetime.LocalTime

operator fun LocalTime.plus(to: LocalTime): LocalTime {
  val minutes = (this.hour * 60 + this.minute) + (to.hour * 60 + to.minute)
  val hours = minutes / 60 % 24
  val minutesRemaining = minutes % 60
  return LocalTime(hours, minutesRemaining)
}
