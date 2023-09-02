package com.addhen.fosdem.data.events.repository.mapper

import com.addhen.fosdem.data.events.api.api.dto.EventDto
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import kotlinx.datetime.LocalDate

internal fun List<EventDto.Days>.toDays() = map { it.toDay() }

internal fun EventDto.Days.toDay() = DayEntity(id = index.toLong(), date = date)

internal fun EventDto.Days.Rooms.toRoom() = RoomEntity(
  id = 0,
  name = name,
)

internal fun List<EventDto.Days.Rooms>.toRooms() = map { it.toRoom() }

internal fun List<EventDto.Days.Rooms.Event.Link>.toLinks() = map { it.toLink() }

internal fun EventDto.Days.Rooms.Event.Link.toLink() = LinkEntity(
  id = 0,
  url = href,
  text = text,
)

internal fun List<EventDto.Days.Rooms.Event.Speaker>.toSpeakers() = map { it.toSpeaker() }
internal fun EventDto.Days.Rooms.Event.Speaker.toSpeaker() = SpeakerEntity(
  id = id,
  name = name,
)

internal fun List<EventDto.Days.Rooms.Event.Attachment>.toAttachments() = map { it.toAttachment() }

internal fun EventDto.Days.Rooms.Event.Attachment.toAttachment() = AttachmentEntity(
  id = 0,
  type = type,
  url = href,
  name = name ?: "",
)

internal fun EventDto.Days.Rooms.Event.toEvent(dayEntity: DayEntity, roomEntity: RoomEntity) = EventEntity(
  id = id,
  title = title,
  date = dayEntity.date,
  room = roomEntity,
  day = DayEntity(0, LocalDate.parse("2023-09-02")),
  start_time = start,
  duration = duration,
  isBookmarked = false,
  abstractText = abstract,
  description = description,
  track = track,
  links = links.toLinks(),
  speakers = persons.toSpeakers(),
  attachments = attachments.toAttachments(),
)

internal fun List<EventDto.Days.Rooms.Event>.toEvents(dayEntity: DayEntity, roomEntity: RoomEntity) = map {
  it.toEvent(dayEntity, roomEntity)
}
