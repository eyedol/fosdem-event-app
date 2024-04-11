// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository.mapper

import com.addhen.fosdem.core.api.minus
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity
import com.addhen.fosdem.model.api.Attachment
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Link
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.Speaker
import com.addhen.fosdem.model.api.Track

internal fun DayEntity.toDay() = Day(id = id, date = date)

internal fun RoomEntity.toRoom() = Room(
  id = id ?: 0L,
  name = name,
)

internal fun List<LinkEntity>.toLinks() = map { it.toLink() }

internal fun LinkEntity.toLink() = Link(
  id = id ?: 0L,
  url = url,
  text = text,
)

internal fun List<SpeakerEntity>.toSpeakers() = map { it.toSpeaker() }
internal fun SpeakerEntity.toSpeaker() = Speaker(
  id = id,
  name = name,
)

internal fun List<AttachmentEntity>.toAttachments() = map { it.toAttachment() }

internal fun AttachmentEntity.toAttachment() = Attachment(
  id = id ?: 0L,
  type = type,
  url = url,
  name = name,
)

internal fun TrackEntity.toTrack() = Track(name, type)

internal fun EventEntity.toEvent() = Event(
  id = id,
  title = title,
  day = day.toDay(),
  room = room.toRoom(),
  startAt = start_time,
  endAt = duration,
  duration = duration - start_time,
  isBookmarked = isBookmarked,
  abstractText = abstractText,
  description = description,
  track = Track(name = track.name, type = track.type),
  url = url,
  links = links.toLinks(),
  speakers = speakers.toSpeakers(),
  attachments = attachments.toAttachments(),
)

internal fun List<EventEntity>.toEvent() = map { it.toEvent() }
