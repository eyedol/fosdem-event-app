// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.core.api.plus
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.tatarka.inject.annotations.Inject

@Inject
class EventsDbDao(
  private val appDatabase: Database,
  private val backgroundDispatcher: AppCoroutineDispatchers,
) : EventsDao {

  override fun getEvents(): Flow<List<EventEntity>> {
    return appDatabase.eventsQueries.selectAll(eventQueriesMapper).asFlow()
      .mapToList(backgroundDispatcher.io).map { it.updateWithRelatedData() }
      .flowOn(backgroundDispatcher.io)
  }

  override fun getAllBookmarkedEvents(): Flow<List<EventEntity>> {
    return appDatabase.eventsQueries.selectAllEventsBookmarked(eventQueriesMapper).asFlow()
      .mapToList(backgroundDispatcher.io).map { it.updateWithRelatedData() }
      .flowOn(backgroundDispatcher.io)
  }

  override fun getEvents(date: LocalDate): Flow<List<EventEntity>> =
    appDatabase.eventsQueries.selectAllByDate(date, eventQueriesMapper).asFlow()
      .mapToList(backgroundDispatcher.io).map { it.updateWithRelatedData() }
      .flowOn(backgroundDispatcher.io)

  override fun getTracks(): Flow<List<TrackEntity>> =
    appDatabase.eventsQueries.selectEventTracks(eventTrackQueriesMapper).asFlow()
      .mapToList(backgroundDispatcher.io).flowOn(backgroundDispatcher.io)

  override fun getEvent(eventId: Long): Flow<EventEntity> {
    return appDatabase.eventsQueries.selectById(eventId, eventQueriesMapper).asFlow()
      .mapToOne(backgroundDispatcher.io).map { it.withRelatedData() }
  }

  override suspend fun toggleBookmark(eventId: Long) = withContext(backgroundDispatcher.io) {
    appDatabase.eventsQueries.toggleBookmark(eventId)
  }

  override suspend fun deleteRelatedData() = withContext(backgroundDispatcher.databaseRead) {
    appDatabase.attachmentsQueries.delete()
    appDatabase.event_attachmentsQueries.delete()
    appDatabase.event_linksQueries.delete()
    appDatabase.event_speakersQueries.delete()
    appDatabase.linksQueries.delete()
  }

  override suspend fun insert(events: List<EventEntity>) =
    withContext(backgroundDispatcher.databaseWrite) {
      events.forEach { eventEntity ->
        // Insert room
        val lastRoomRowId = selInsertRoom(eventEntity.room)
        // Insert event
        upsertEvent(eventEntity, lastRoomRowId)
      }
    }

  override suspend fun addDays(days: List<DayEntity>) =
    withContext(backgroundDispatcher.databaseWrite) {
      days.forEach {
        appDatabase.daysQueries.insert(it.id, it.date)
      }
    }

  override suspend fun getDays(): List<DayEntity> = withContext(backgroundDispatcher.databaseRead) {
    appDatabase.daysQueries.selectAll().executeAsList().toDays()
  }

  private fun selInsertRoom(roomEntity: RoomEntity): Long {
    // Select room id if room exists else insert and return id of the room.
    // Doing this because the room_id is needed to be added to the EventEntity
    // before the event is inserted into the table.
    val roomId = appDatabase.roomsQueries.selectIdByName(roomEntity.name).executeAsOneOrNull()
    return if (roomId != null) {
      roomId
    } else {
      appDatabase.roomsQueries.insert(roomEntity.id, roomEntity.name)
      appDatabase.roomsQueries.findInsertRowid().executeAsOne()
    }
  }

  private fun selInsertSpeaker(speakerEntity: SpeakerEntity): Long {
    // Select speaker if speaker exists else insert and return id of the speaker.
    val speakerId = appDatabase.speakersQueries.selectById(speakerEntity.id).executeAsOneOrNull()
    return if (speakerId != null) {
      speakerId
    } else {
      appDatabase.speakersQueries.insert(speakerEntity.id, speakerEntity.name)
      appDatabase.speakersQueries.findInsertRowid().executeAsOne()
    }
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
      duration = duration,
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

  private suspend fun upsertEvent(
    entity: EventEntity,
    lastRoomRowId: Long,
  ) {
    // Update event if exists else insert
    appDatabase.transactionWithContext(backgroundDispatcher.databaseWrite) {
      appDatabase.eventsQueries.updateById(
        lastRoomRowId,
        entity.date,
        entity.start_time,
        entity.start_time + entity.duration,
        entity.isBookmarked,
        entity.title,
        entity.abstractText,
        entity.description,
        entity.track.name,
        entity.track.type,
        entity.id,
      )
      if (appDatabase.eventsQueries.changes().executeAsOne() == 0L) {
        appDatabase.eventsQueries.insert(
          entity.id,
          entity.date,
          lastRoomRowId,
          entity.start_time,
          entity.start_time + entity.duration,
          entity.title,
          entity.isBookmarked,
          entity.abstractText,
          entity.description,
          entity.track.name,
          entity.track.type,
        )
      }
      insertRelatedData(entity)
    }
  }

  private fun insertRelatedData(eventEntity: EventEntity) {
    // update attachment if exists else insert
    for (attachment in eventEntity.attachments) {
      appDatabase.attachmentsQueries.insert(
        id = attachment.id,
        type = attachment.type,
        url = attachment.url,
        name = attachment.name,
      )
      val lastAttachmentRowId = appDatabase.attachmentsQueries.findInsertRowid().executeAsOne()
      appDatabase.event_attachmentsQueries.insert(lastAttachmentRowId, eventEntity.id)
    }

    // update speakers if exists else insert
    for (speaker in eventEntity.speakers) {
      val speakerId = selInsertSpeaker(speaker)
      appDatabase.event_speakersQueries.insert(speakerId, eventEntity.id)
    }

    // Insert links
    for (link in eventEntity.links) {
      appDatabase.linksQueries.insert(link.id, link.url, link.text)
      val lastLinkRowId = appDatabase.linksQueries.findInsertRowid().executeAsOne()
      appDatabase.event_linksQueries.insert(lastLinkRowId, eventEntity.id)
    }
  }
}
