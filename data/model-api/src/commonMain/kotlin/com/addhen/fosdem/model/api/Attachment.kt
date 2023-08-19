package com.addhen.fosdem.model.api

data class Attachment(
  val id: Long,
  val eventId: Long,
  val type: String,
  val url: String,
)
