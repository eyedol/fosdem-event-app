// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Attachments
import com.addhen.fosdem.data.sqldelight.api.Days
import com.addhen.fosdem.data.sqldelight.api.Links
import com.addhen.fosdem.data.sqldelight.api.Rooms
import com.addhen.fosdem.data.sqldelight.api.Speakers
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.transactionWithContext
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class EventsDbDao(
  private val clock: Clock,
  private val timeZone: TimeZone,
  private val appDatabase: Database,
  private val backgroundDispatcher: AppCoroutineDispatchers,
) : EventsDao {
  override fun getEvents(dayId: Long): Flow<List<EventEntity>> {
    return appDatabase.eventsQueries
      .selectAll(dayId, eventQueriesMapper)
      .asFlow()
      .mapToList(backgroundDispatcher.io)
      .map { it.updateWithRelatedData() }
      .flowOn(backgroundDispatcher.io)
  }

  override fun getEvent(eventId: Long): Flow<EventEntity> {
    TODO("Not yet implemented")
  }

  override fun toggleBookmark(eventId: Long) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteAll() {
    appDatabase.transactionWithContext(backgroundDispatcher.databaseRead) {
      appDatabase.eventsQueries.deleteAll()
    }
  }

  override suspend fun insert(events: List<EventEntity>) {
    appDatabase.transactionWithContext(backgroundDispatcher.databaseRead) {
      events.forEach { eventEntity ->
        appDatabase.daysQueries.insert(eventEntity.day.id, eventEntity.day.date)
        // Add speakers as a separate insert query
        eventEntity.speakers.forEach {
          appDatabase.speakersQueries.insert(it.id, it.name)
          appDatabase.event_speakersQueries.insert(it.id, eventEntity.id)
        }
        // Add links as a separate insert query
        eventEntity.links.forEach { linkEntity ->
          appDatabase.linksQueries.insert(linkEntity.id, linkEntity.url, linkEntity.text)
          val lastLinkRowId = appDatabase.linksQueries.findInsertRowid().executeAsOne()
          appDatabase.event_linksQueries.insert(lastLinkRowId, eventEntity.id)
        }

        // Add room as a separate insert query
        appDatabase.roomsQueries.insert(id = eventEntity.room.id, name = eventEntity.room.name)

        // Add attachments as a separate insert query.
        eventEntity.attachments.forEach { attachmentEntity ->
          appDatabase.attachmentsQueries.insert(
            id = null,
            type = attachmentEntity.type,
            url = attachmentEntity.url
          )
          val lastAttachmentRowId = appDatabase.attachmentsQueries.findInsertRowid().executeAsOne()
          appDatabase.event_attachmentsQueries.insert(lastAttachmentRowId, eventEntity.id)
        }

        // Add events as a separate insert query
        appDatabase.eventsQueries.insert(
          eventEntity.id,
          eventEntity.day.id,
          eventEntity.room.id,
          eventEntity.start_time,
          eventEntity.duration,
          eventEntity.title,
          eventEntity.isBookmarked,
          eventEntity.abstractText,
          eventEntity.description,
          eventEntity.track
        )
      }
    }
  }

  private val eventQueriesMapper = {
      id: Long,
      _: Long?,
      _: Long?,
      start_time: LocalTime?,
      duration: LocalTime?,
      isBookmarked: Boolean,
      title: String?,
      abstract_text: String?,
      description: String?,
      track: String?,
      id_: Long,
      date_: LocalDate?,
      id__: Long,
      name: String?,

    ->
    EventEntity(
      id = id,
      title = title ?: "",
      day = DayEntity(
        id = id_,
        date = date_ ?: LocalDate.now(),
      ),
      room = RoomEntity(
        id = id__,
        name = name ?: "",
      ),
      start_time = start_time ?: LocalTime.now(),
      duration = duration ?: LocalTime.now(),
      isBookmarked = isBookmarked,
      abstractText = abstract_text ?: "",
      description = description ?: "",
      track = track ?: "",
      links = emptyList(),
      speakers = emptyList(),
      attachments = emptyList(),
    )
  }

  fun LocalDate.Companion.now(): LocalDate = clock.now().toLocalDateTime(timeZone).date

  fun LocalTime.Companion.now(): LocalTime = clock.now().toLocalDateTime(timeZone).time

  private fun Days.toDay() = Day(id, date ?: LocalDate.now())

  private fun Rooms.toRoom() = Room(id = id, name = name ?: "")

  private fun List<Links>.toLinks() = map { it.toLink() }

  private fun List<Speakers>.toSpeakers() = map { it.toSpeaker() }

  private fun List<Attachments>.toAttachments() = map { it.toAttachment() }
  private fun Links.toLink() = LinkEntity(
    id = id,
    url = url,
    text = text,
  )

  private fun Speakers.toSpeaker() = SpeakerEntity(
    id = id,
    name = name,
  )

  private fun Attachments.toAttachment() = AttachmentEntity(
    id = id,
    type = type ?: "",
    url = url ?: "",
  )

  private fun List<EventEntity>.updateWithRelatedData(): List<EventEntity> {
    return map { event ->
      val speakers = appDatabase.event_speakersQueries
        .selectSpeakers(event.day.id)
        .executeAsList()
        .map { it.toSpeaker() }

      val links = appDatabase.event_linksQueries
        .selectLinks(event.day.id)
        .executeAsList()
        .map { it.toLink() }

      val attachments = appDatabase.event_attachmentsQueries
        .selectAttachments(event.day.id)
        .executeAsList()
        .map { it.toAttachment() }

      event.copy(
        speakers = speakers,
        links = links,
        attachments = attachments
      )
    }
  }
}
