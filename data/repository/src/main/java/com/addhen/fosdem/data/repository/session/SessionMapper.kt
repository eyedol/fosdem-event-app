package com.addhen.fosdem.data.repository.session

import com.addhen.fosdem.data.db.room.entity.*
import com.addhen.fosdem.data.model.*
import com.addhen.fosdem.platform.parser.Schedule
import java.util.Date

fun SessionSpeakerLinkJoinEntity.toSession(
    speakers: List<SpeakerEntity>,
    links: List<LinkEntity>
): Session {
    val localSpeakers = this.speakers.map { speakerEntity ->
        speakers.first { it.id == speakerEntity.id }.toSpeaker()
    }

    val localLinks = this.links.map { linkEntity ->
        links.first { it.id == linkEntity.id }.toLink()
    }
    return Session(
        id = session.id,
        startTime = Date(session.start),
        durationTime = Date(session.duration),
        title = session.title,
        description = session.description,
        abstractText = session.abstractText,
        day = session.day.toDay(),
        room = session.room.toRoom(),
        track = session.track.toTrack(),
        speakers = localSpeakers,
        links = localLinks
    )
}

fun RoomEntity.toRoom(): Room {
    return Room(
        name = name,
        building = building
    )
}

fun TrackEntity.toTrack(): Track {
    return Track(
        id = id,
        name = name,
        type = Track.Type.valueOf(type)
    )
}

fun LinkEntity.toLink(): Link {
    return Link(
        id = id,
        href = href,
        text = text,
        sessionId = sessionId
    )
}

fun DayEntity.toDay(): Day {
    return Day(
        index = index,
        date = Date(date)
    )
}

fun SpeakerEntity.toSpeaker(): Speaker {
    return Speaker(
        id = id,
        name = name,
        sessionId = sessionId
    )
}

fun Schedule.toSessions(): List<Session> {
    val sessions = mutableListOf<Session>()
    days.forEach { sessions.addAll(it.events) }
    return sessions
}

fun Room.toRoomEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        name = name,
        building = building
    )
}

fun Track.toTrackEntity(): TrackEntity {
    return TrackEntity(
        id = id,
        name = name,
        type = type.name
    )
}

fun Link.toLinkEntity(): LinkEntity {
    return LinkEntity(
        id = id,
        href = href,
        text = text,
        sessionId = sessionId
    )
}

fun Day.toDayEntity(): DayEntity {
    return DayEntity(
        index = index,
        date = date.time
    )
}

fun Session.toSessionEntity(): SessionEntity {
    return SessionEntity(
        id = id,
        start = startTime.time,
        duration = durationTime.time,
        title = title,
        description = description,
        abstractText = abstractText,
        day = day.toDayEntity(),
        room = room.toRoomEntity(),
        track = track.toTrackEntity()
    )
}

fun Speaker.toSpeakerEntity(): SpeakerEntity {
    return SpeakerEntity(
        id = id,
        name = name,
        sessionId = sessionId
    )
}

fun List<Session>.toSessionEntities(): List<SessionEntity> {
    return map { it.toSessionEntity() }
}

fun List<Speaker>.toSpeakerEntities(): List<SpeakerEntity> {
    return map { it.toSpeakerEntity() }
}

fun List<Link>.toLinkEntities(): List<LinkEntity> {
    return map { it.toLinkEntity() }
}
