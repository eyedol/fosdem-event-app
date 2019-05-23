package com.addhen.fosdem.data.db

import com.addhen.fosdem.data.db.room.entity.*

interface SessionDatabase {

    suspend fun sessions(): List<SessionSpeakerLinkJoinEntity>
    suspend fun speakers(): List<SpeakerEntity>
    suspend fun links(): List<LinkEntity>
    suspend fun save(
        sessions: List<SessionEntity>,
        links: List<LinkEntity>,
        speakers: List<SpeakerEntity>,
        joinLinks: List<SessionLinkJoinEntity>,
        joinSpeakers: List<SessionSpeakerJoinEntity>
    )
}
