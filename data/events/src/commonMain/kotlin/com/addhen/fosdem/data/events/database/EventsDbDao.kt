// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.core.api.timeZoneBrussels
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Attachments
import com.addhen.fosdem.data.sqldelight.api.Days
import com.addhen.fosdem.data.sqldelight.api.Links
import com.addhen.fosdem.data.sqldelight.api.Speakers
import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.entities.TrackEntity
import com.addhen.fosdem.data.sqldelight.api.transactionWithContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import me.tatarka.inject.annotations.Inject
import kotlin.time.Duration.Companion.minutes

@Inject
class EventsDbDao(
  private val appDatabase: Database,
  private val backgroundDispatcher: AppCoroutineDispatchers,
) : EventsDao {

  override fun getEvents(): Flow<List<EventEntity>> {
    return appDatabase.eventsQueries
      .selectAll(eventQueriesMapper)
      .asFlow()
      .mapToList(backgroundDispatcher.io)
      .map { it.updateWithRelatedData() }
      .flowOn(backgroundDispatcher.io)
  }

  override fun getEvents(date: LocalDate): Flow<List<EventEntity>> = appDatabase
    .eventsQueries
    .selectAllByDate(date, eventQueriesMapper)
    .asFlow()
    .mapToList(backgroundDispatcher.io)
    .map { it.updateWithRelatedData() }
    .flowOn(backgroundDispatcher.io)

  override fun getTracks(): Flow<List<TrackEntity>> = appDatabase
    .eventsQueries
    .selectEventTracks(eventTrackQueriesMapper)
    .asFlow()
    .mapToList(backgroundDispatcher.io)
    .flowOn(backgroundDispatcher.io)

  override fun getEvent(eventId: Long): Flow<EventEntity> {
    return appDatabase.eventsQueries.selectById(eventId, eventQueriesMapper)
      .asFlow()
      .mapToOne(backgroundDispatcher.io)
      .map { it.withRelatedData() }
  }

  override suspend fun toggleBookmark(eventId: Long) = withContext(backgroundDispatcher.io) {
    appDatabase.eventsQueries.toggleBookmark(eventId)
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
        appDatabase.roomsQueries.insert(id = null, name = eventEntity.room.name)
        val lastRoomRowId: Long = appDatabase.roomsQueries.findInsertRowid().executeAsOne()

        // Add attachments as a separate insert query.
        eventEntity.attachments.forEach { attachmentEntity ->
          appDatabase.attachmentsQueries.insert(
            id = null,
            type = attachmentEntity.type,
            url = attachmentEntity.url,
            name = attachmentEntity.name,
          )
          val lastAttachmentRowId = appDatabase.attachmentsQueries.findInsertRowid().executeAsOne()
          appDatabase.event_attachmentsQueries.insert(lastAttachmentRowId, eventEntity.id)
        }

        // Add events as a separate insert query
        appDatabase.eventsQueries.insert(
          eventEntity.id,
          eventEntity.date,
          lastRoomRowId,
          eventEntity.start_time,
          eventEntity.duration,
          eventEntity.title,
          eventEntity.isBookmarked,
          eventEntity.abstractText,
          eventEntity.description,
          eventEntity.track.name,
          eventEntity.track.type,
        )
      }
    }
  }

  override suspend fun addDays(days: List<DayEntity>) {
    appDatabase.transactionWithContext(backgroundDispatcher.databaseRead) {
      days.forEach {
        appDatabase.daysQueries.insert(it.id, it.date)
      }
    }
  }

  override suspend fun getDays(): List<DayEntity> = withContext(backgroundDispatcher.io) {
    appDatabase.daysQueries.selectAll().executeAsList().toDays()
  }

  private val eventTrackQueriesMapper = {
      track_name: String?,
      track_type: String?,
    ->
    TrackEntity(
      name = track_name ?: "",
      type = track_type ?: "",
    )
  }

  private val eventQueriesMapper = {
      id: Long,
      _: Long?,
      date: LocalDate,
      start_time: LocalTime,
      duration: LocalTime,
      isBookmarked: Boolean,
      title: String,
      abstract_text: String,
      description: String?,
      track_name: String?,
      track_type: String?,
      id_: Long,
      date_: LocalDate,
      id__: Long,
      name: String?,
    ->
    EventEntity(
      id = id,
      title = title,
      date = date,
      room = RoomEntity(
        id = id__,
        name = name ?: "",
      ),
      day = DayEntity(id_, date_),
      start_time = start_time,
      duration = start_time.plusMinutes(duration),
      isBookmarked = isBookmarked,
      abstractText = abstract_text,
      description = description ?: "",
      track = TrackEntity(track_name ?: "", track_type ?: ""),
      links = emptyList(),
      speakers = emptyList(),
      attachments = emptyList(),
    )
  }

  private fun List<Days>.toDays() = map { it.toDay() }

  private fun Days.toDay() = DayEntity(id = id, date = date)

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
    name = name ?: "",
  )

  private fun EventEntity.withRelatedData(): EventEntity {
    val speakers = appDatabase.event_speakersQueries
      .selectSpeakers(date, id)
      .executeAsList()
      .map { it.toSpeaker() }

    val links = appDatabase.event_linksQueries
      .selectLinks(date, id)
      .executeAsList()
      .map { it.toLink() }

    val attachments = appDatabase.event_attachmentsQueries
      .selectAttachments(date, id)
      .executeAsList()
      .map { it.toAttachment() }

    return copy(
      speakers = speakers,
      links = links,
      attachments = attachments,
    )
  }

  private fun List<EventEntity>.updateWithRelatedData(): List<EventEntity> {
    return map { it.withRelatedData() }
  }

  private fun LocalTime.plusMinutes(to: LocalTime, zone: TimeZone = timeZoneBrussels): LocalTime {
    val atDate = Clock.System.now().toLocalDateTime(zone).date
    return (LocalDateTime(atDate, this).toInstant(zone) + to.minute.minutes)
      .toLocalDateTime(zone)
      .time
  }
}
