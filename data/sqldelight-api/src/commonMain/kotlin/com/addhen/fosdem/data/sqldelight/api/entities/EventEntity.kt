// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EventEntity(
  val id: Long,
  val start_time: LocalTime,
  val duration: LocalTime,
  val title: String,
  val description: String,
  val isBookmarked: Boolean,
  val abstractText: String,
  val url: String,
  val day: DayEntity,
  val date: LocalDate,
  val room: RoomEntity,
  val track: TrackEntity,
  val links: List<LinkEntity>,
  val speakers: List<SpeakerEntity>,
  val attachments: List<AttachmentEntity>,
)
