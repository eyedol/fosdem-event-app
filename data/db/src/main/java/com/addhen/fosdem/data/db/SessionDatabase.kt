package com.addhen.fosdem.data.db

import com.addhen.fosdem.data.db.room.entity.LinkEntity
import com.addhen.fosdem.data.db.room.entity.SessionEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerLinkJoinEntity
import com.addhen.fosdem.data.db.room.entity.SpeakerEntity

interface SessionDatabase {

    suspend fun sessions(): List<SessionSpeakerLinkJoinEntity>
    suspend fun speakers(): List<SpeakerEntity>
    suspend fun links(): List<LinkEntity>
    suspend fun save(
        sessions: List<SessionEntity>,
        links: List<LinkEntity>,
        speakers: List<SpeakerEntity>
    )
}
