package com.addhen.fosdem.data.events.database

import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

val link = LinkEntity(
  id = 1,
  url = "https://link1.com",
  text = "Video recording1"
)

val speaker = SpeakerEntity(
  id = 1,
  name = "FOSDEM Staff"
)

val attachment = AttachmentEntity(
  id = 1,
  type = "slides",
  url = "https://attachment1.com"
)

val room = RoomEntity(
  id = 1,
  name = "Janson"
)

val link2 = LinkEntity(
  id = 2,
  url = "https://link2.com",
  text = "Video recording1"
)

val speaker2 = SpeakerEntity(
  id = 2,
  name = "Nick Vidal"
)

val attachment2 = AttachmentEntity(
  id = 2,
  type = "slides",
  url = "https://attachment2.com"
)

val room2 = RoomEntity(
  id = 2,
  name = "Janson"
)


val link3 = LinkEntity(
  id = 3,
  url = "https://link3.come",
  text = "FOSSi Foundation website"
)

val room3 = RoomEntity(
  id = 3,
  name = "K.1.105 (La Fontaine)"
)

val speaker3 = SpeakerEntity(
  id = 3,
  name = "Philipp Wagner"
)

val attachment3 = AttachmentEntity(
  id = 3,
  type = "presentation slides",
  url = "https://attachment3.com"
)

val day = DayEntity(
  id = 1,
  date = LocalDate.parse("2023-02-04")
)

val day2 = DayEntity(
  id = 2,
  date = LocalDate.parse("2023-02-05")
)

val day1Event = EventEntity(
  id = 1,
  start_time = LocalTime.parse("09:30"),
  duration = LocalTime.parse("00:25"),
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk",
  day = day,
  date = day.date,
  links = listOf(link),
  speakers = listOf(speaker),
  room = room,
  track = "Keynotes",
  attachments = listOf(attachment)
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
  links = listOf(link2),
  speakers = listOf(speaker2),
  room = room2,
  track = "Keynotes",
  attachments = listOf(attachment2)
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
  links = listOf(link3),
  speakers = listOf(speaker3),
  room = room3,
  track = "Main Track - K Building",
  attachments = listOf(attachment3)
)

val events = listOf(day1Event, day2Event, day3Event)

