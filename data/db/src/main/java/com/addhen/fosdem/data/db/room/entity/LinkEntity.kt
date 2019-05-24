package com.addhen.fosdem.data.db.room.entity

import androidx.room.*

@Entity(
    tableName = "link", indices = [(Index(value = ["session_id"], unique = true))],
    foreignKeys = [
        (ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        ))]
)
data class LinkEntity(
    @PrimaryKey val id: Long,
    val href: String,
    val text: String,
    @ColumnInfo(name = "session_id")
    val sessionId: Long = 0
)
