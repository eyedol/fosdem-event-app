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
        speakers.first { it.id == speakerEntity.speakerId }.toSpeaker()
    }

    val localLinks = this.links.map { linkEntity ->
        links.first { it.id == linkEntity.linkId }.toLink()
    }
    return Session(
        id = session.id,
        day = session.day,
        startTime = Date(session.start),
        durationTime = Date(session.duration),
        title = session.title,
        description = session.description,
        abstract = session.abstract,
        room = session.room!!.toRoom(),
        track = session.track!!.toTrack(),
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
        text = text
    )
}

fun SpeakerEntity.toSpeaker(): Speaker {

    return Speaker(
        id = id,
        name = name
    )
}

fun Schedule.toSessions(): List<Session> {
    val sessions = mutableListOf<Session>()
    days.forEach { sessions.addAll(it.events) }
    return sessions
}
