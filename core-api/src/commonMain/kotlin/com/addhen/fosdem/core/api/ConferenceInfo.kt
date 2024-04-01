package com.addhen.fosdem.core.api

private val year = currentYear.toString()

data class ConferenceInfo(
  val name: String,
  val fullYear: String,
  val shortYear: String,
  val location: String
)

val FosdemConference = ConferenceInfo(
  name = "FOSDEM",
  fullYear = year,
  shortYear = year.substring(year.length - 2),
  location = "@ Brussels, Belgium"
)
