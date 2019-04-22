package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "session_link_join", primaryKeys = ["session_id", "link_id"],
    foreignKeys = [
        (ForeignKey(
            entity = SessionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("session_id"),
            onDelete = ForeignKey.CASCADE
        )),
        (ForeignKey(
            entity = LinkEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("link_id"),
            onDelete = ForeignKey.CASCADE
        ))]
)
data class SessionLinkJoinEntity(
    @ColumnInfo(name = "session_id")
    val sessionId: Int,
    @ColumnInfo(name = "link_id", index = true)
    val linkId: Int
)
