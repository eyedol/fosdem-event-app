package com.addhen.fosdem.core.api

data class ConferenceInfo(
  val name: String,
  val fullYear: String,
  val shortYear: String
)

val year = currentYear.toString()

val FosdemConference = ConferenceInfo(
  name = "FOSDEM",
  fullYear = year,
  shortYear = year.substring(year.length - 2)
)
