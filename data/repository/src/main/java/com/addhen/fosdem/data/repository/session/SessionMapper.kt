package com.addhen.fosdem.data.repository.session

import com.addhen.fosdem.data.db.room.entity.*
import com.addhen.fosdem.data.model.*

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
        start = session.start,
        duration = session.duration,
        title = session.title,
        description = session.description,
        room = session.room!!.toRoom(),
        track = session.track!!.toTrack(),
        speakers = localSpeakers,
        links = localLinks
    )
}

fun RoomEntity.toRoom(): Room {

    return Room(
        id = id,
        name = name,
        building = building,
        capacity = capacity
    )
}

fun TrackEntity.toTrack(): Track {

    return Track(
        id = id,
        name = name
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
