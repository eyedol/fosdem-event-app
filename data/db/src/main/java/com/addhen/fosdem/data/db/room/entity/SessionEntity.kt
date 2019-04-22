package com.addhen.fosdem.data.db.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int,
    val start: String,
    val duration: String,
    val title: String,
    val type: String,
    val description: String,
    @Embedded val room: RoomEntity?,
    @Embedded val track: TrackEntity?
)
