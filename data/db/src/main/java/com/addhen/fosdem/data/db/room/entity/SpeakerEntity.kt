package com.addhen.fosdem.data.db.room.entity

import androidx.room.*

@Entity(
    tableName = "speaker",
    indices = [(Index(value = arrayOf("session_id"), unique = true))],
    foreignKeys = [
        (ForeignKey(
            entity = SessionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("session_id"),
            onDelete = ForeignKey.CASCADE
        ))]
)
data class SpeakerEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "session_id")
    val sessionId: Long = 0
)

