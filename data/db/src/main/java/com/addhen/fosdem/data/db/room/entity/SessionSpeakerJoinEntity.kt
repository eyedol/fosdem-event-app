package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "session_speaker_join", primaryKeys = ["session_id", "speaker_id"],
    foreignKeys = [
        (ForeignKey(
            entity = SessionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("session_id"),
            onDelete = ForeignKey.CASCADE
        )),
        (ForeignKey(
            entity = SpeakerEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("speaker_id"),
            onDelete = ForeignKey.CASCADE
        ))]
)
data class SessionSpeakerJoinEntity(
    @ColumnInfo(name = "session_id")
    val sessionId: Long,
    @ColumnInfo(name = "speaker_id", index = true)
    val speakerId: Long
)
