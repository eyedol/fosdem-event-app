package com.addhen.fosdem.data.db.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SessionSpeakerLinkJoinEntity(
    @Embedded val session: SessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        entity = SessionSpeakerJoinEntity::class
    )
    val speakers: List<SessionSpeakerJoinEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        entity = SessionLinkJoinEntity::class
    )
    val links: List<SessionLinkJoinEntity> = emptyList()
)
