package com.addhen.fosdem.data.sqldelight.api.entities

data class LinkEntity(
  val id: Long,
  val url: String,
  val text: String,
  val event_id: Long = 0
)
