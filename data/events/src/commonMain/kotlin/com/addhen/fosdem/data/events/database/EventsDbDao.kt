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
import com.addhen.fosdem.data.sqldelight.api.Events
import com.addhen.fosdem.data.sqldelight.api.Links
import com.addhen.fosdem.data.sqldelight.api.Rooms
import com.addhen.fosdem.data.sqldelight.api.Speakers
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import com.addhen.fosdem.data.sqldelight.api.transactionWithContext
import com.addhen.fosdem.model.api.Attachment
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.Link
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
    return appDatabase.eventsQueries.selectAll(dayId, eventQueriesMapper)
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

  override suspend fun insert(movies: List<Events>) {
    TODO("Not yet implemented")
  }

  private val eventQueriesMapper = {
      id: Long,
      day_id: Long?,
      room_id: Long?,
      start_time: LocalTime?,
      duration: LocalTime?,
      date: LocalDate?,
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
      track = String(),
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
  private fun Links.toLink() = Link(
    id = id,
    url = url,
    text = text,
  )

  private fun Speakers.toSpeaker() = SpeakerEntity(
    id = id,
    name = name,
  )

  private fun Attachments.toAttachment() = Attachment(
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

      event.copy(
        speakers = speakers,
      )
    }
  }
}
