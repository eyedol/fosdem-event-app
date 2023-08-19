// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

import kotlinx.datetime.LocalTime

data class Event(
  val id: Long,
  val startTime: LocalTime,
  val duration: LocalTime,
  val title: String,
  val description: String,
  val abstractText: String,
  val day: Day,
  val room: Room,
  val track: Track,
  val links: List<Link>,
  val speakers: List<Speaker>,
)
