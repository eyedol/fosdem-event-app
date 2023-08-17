// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

import kotlinx.datetime.LocalTime

data class EventEntity(
  val id: Long,
  val day_id: Long,
  val room_id: Long,
  val start_time: LocalTime,
  val duration: LocalTime,
  val title: String,
  val description: String,
  val subtitle: String,
  val track: String,
)
