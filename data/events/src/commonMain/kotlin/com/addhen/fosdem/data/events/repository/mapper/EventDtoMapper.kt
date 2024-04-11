// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository.mapper

import com.addhen.fosdem.data.events.api.api.dto.EventDto
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity

internal fun List<EventDto.Days>.toDays() = map { it.toDay() }

internal fun EventDto.Days.toDay() = DayEntity(id = index.toLong(), date = date)

internal fun EventDto.Days.Rooms.toRoom() = RoomEntity(
  id = null, // Auto incremented in the database
  name = name,
)

internal fun List<EventDto.Days.Rooms.Event.Link>.toLinks() = map { it.toLink() }

internal fun EventDto.Days.Rooms.Event.Link.toLink() = LinkEntity(
  id = null, // Auto incremented in the database
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
  id = null, // Auto incremented in the database
  type = type,
  url = href,
  name = name ?: "",
)

internal fun EventDto.Days.Rooms.Event.toEvent(dayEntity: DayEntity, roomEntity: RoomEntity) =
  EventEntity(
    id = id,
    title = title,
    date = dayEntity.date,
    room = roomEntity,
    day = dayEntity,
    start_time = start,
    duration = duration,
    isBookmarked = false,
    abstractText = abstract,
    description = description,
    url = url,
    track = TrackEntity(track, type),
    links = links.toLinks(),
    speakers = persons.toSpeakers(),
    attachments = attachments.toAttachments(),
  )

internal fun List<EventDto.Days.Rooms.Event>.toEvents(
  dayEntity: DayEntity,
  roomEntity: RoomEntity,
) = map {
  it.toEvent(dayEntity, roomEntity)
}
