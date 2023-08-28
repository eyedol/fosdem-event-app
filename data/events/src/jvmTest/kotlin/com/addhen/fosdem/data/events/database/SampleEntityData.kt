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
  url = "https://video.fosdem.org/2023/D.matrix/cascaded_selective_forwarding_units.webm",
  text = "Video recording (WebM/VP9)"
)

val speaker = SpeakerEntity(
  id = 1,
  name = "FOSDEM Staff"
)

val attachment = AttachmentEntity(
  id = 1,
  type = "slides",
  url = "https://fosdem.org/2023/schedule/event/sds_vhost_user_blk/attachments/slides/5444/export/events/attachments/sds_vhost_user_blk/slides/5444/stefanha_fosdem_2023.pdf",
  event_id = 1
)

val link2 = LinkEntity(
  id = 2,
  url = "https://video.fosdem.org/2023/Janson/celebrating_25_years_of_open_source.webm",
  text = "Video recording (WebM/VP9, 101M)"
)

val speaker2 = SpeakerEntity(
  id = 2,
  name = "Nick Vidal"
)

val attachment2 = AttachmentEntity(
  id = 2,
  type = "slides",
  url = "https://fosdem.org/2023/schedule/event/sds_vhost_user_blk/attachments/slides/5444/export/events/attachments/sds_vhost_user_blk/slides/5444/stefanha_fosdem_2023.pdf",
  event_id = 1
)

val day = DayEntity(
  id = 1,
  date = LocalDate.parse("2023-02-04")
)

val room = RoomEntity(
  id = 1,
  name = "Janson"
)

val day2 = DayEntity(
  id = 2,
  date = LocalDate.parse("2023-02-05")
)

val room2 = RoomEntity(
  id = 2,
  name = "Janson"
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
  links = emptyList(),
  speakers = listOf(speaker),
  room = room,
  track = "Keynotes",
  attachments = emptyList()
)

val day2Event = EventEntity(
  id = 2,
  start_time = LocalTime.parse("09:30"),
  duration = LocalTime.parse("00:25"),
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk",
  day = day,
  links = emptyList(),
  speakers = listOf(speaker),
  room = room,
  track = "Keynotes",
  attachments = emptyList()
)



