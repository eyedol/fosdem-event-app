package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Long,
    val start: Long,
    val duration: Long,
    val day: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "abstractText")
    val abstractText: String,
    @Embedded val room: RoomEntity?,
    @Embedded val track: TrackEntity?
)
