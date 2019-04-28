package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo

data class RoomEntity(
    @ColumnInfo(name = "room_id")
    val id: Long,
    @ColumnInfo(name = "room_name")
    val name: String,
    @ColumnInfo(name = "room_building")
    val building: String
)

