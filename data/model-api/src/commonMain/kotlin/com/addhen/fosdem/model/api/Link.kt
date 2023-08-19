package com.addhen.fosdem.model.api

data class Link(
  val id: Long,
  val url: String,
  val text: String,
  val eventId: Long,
)
