package com.addhen.fosdem.data.db.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SessionSpeakerLinkJoinEntity(
    @Embedded val session: SessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        entity = SpeakerEntity::class
    )
    val speakers: List<SpeakerEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        entity = LinkEntity::class
    )
    val links: List<LinkEntity> = emptyList()
)
