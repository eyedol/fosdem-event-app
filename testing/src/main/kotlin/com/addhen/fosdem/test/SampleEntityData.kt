// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.test

import com.addhen.fosdem.core.api.plus
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

val link = LinkEntity(
  id = 1,
  url = "https://link1.com",
  text = "Video recording1",
)

val speaker = SpeakerEntity(
  id = 1,
  name = "FOSDEM Staff",
)

val attachment = AttachmentEntity(
  id = 1,
  type = "slides",
  url = "https://attachment1.com",
  name = "attachment 1",
)

val room = RoomEntity(
  id = 1,
  name = "Janson",
)

val link2 = LinkEntity(
  id = 2,
  url = "https://link2.com",
  text = "Video recording1",
)

val speaker2 = SpeakerEntity(
  id = 2,
  name = "Nick Vidal",
)

val attachment2 = AttachmentEntity(
  id = 2,
  type = "slides",
  url = "https://attachment2.com",
  name = "attachment 2",
)

val room2 = RoomEntity(
  id = 2,
  name = "Janson",
)

val link3 = LinkEntity(
  id = 3,
  url = "https://link3.come",
  text = "FOSSi Foundation website",
)

val room3 = RoomEntity(
  id = 3,
  name = "K.1.105 (La Fontaine)",
)

val speaker3 = SpeakerEntity(
  id = 3,
  name = "Philipp Wagner",
)

val attachment3 = AttachmentEntity(
  id = 3,
  type = "presentation slides",
  url = "https://attachment3.com",
  name = "attachment 3",
)

val day = DayEntity(
  id = 1,
  date = LocalDate.parse("2023-02-04"),
)

val day2 = DayEntity(
  id = 2,
  date = LocalDate.parse("2023-02-05"),
)

val day1Event = EventEntity(
  id = 1,
  start_time = LocalTime.parse("09:30"),
  duration = LocalTime.parse("00:25"),
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk",
  url = "https://day1.event.com",
  day = day,
  date = day.date,
  links = listOf(link),
  speakers = listOf(speaker),
  room = room,
  track = TrackEntity("Keynotes", "keynotes"),
  attachments = listOf(attachment),
)

val day2Event = EventEntity(
  id = 2,
  start_time = LocalTime.parse("11:40"),
  duration = LocalTime.parse("00:15"),
  title = "An open source and open design educational robot",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSSbot is a free and open source and open design.",
  day = day2,
  date = day2.date,
  url = "https://day2.event.com",
  links = listOf(link2),
  speakers = listOf(speaker2),
  room = room2,
  track = TrackEntity("Keynotes", "keynotes"),
  attachments = listOf(attachment2),
)

val day3Event = EventEntity(
  id = 3,
  start_time = LocalTime.parse("11:00"),
  duration = LocalTime.parse("00:50"),
  title = "Can we do an open source chip design in 45 minutes?",
  description = "In the last decades, producing a do",
  isBookmarked = false,
  abstractText = "the art and craft of making computer chips with.",
  day = day2,
  date = day2.date,
  url = "https://day3.event.com",
  links = listOf(link3),
  speakers = listOf(speaker3),
  room = room3,
  track = TrackEntity("Main Track - K Building", "keynotes"),
  attachments = listOf(attachment3),
)

val events = listOf(day1Event, day2Event, day3Event)

fun EventEntity.setDurationTime(): EventEntity {
  return copy(duration = start_time + duration)
}

fun List<EventEntity>.setDurationTime(): List<EventEntity> = map {
  it.setDurationTime()
}

fun EventEntity.setRoomId(roomId: Long = 1): EventEntity {
  return copy(room = RoomEntity(id = roomId, name = room.name))
}
