package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo

data class TrackEntity(
    @ColumnInfo(name = "track_id")
    val id: Int,
    @ColumnInfo(name = "track_name")
    val name: String
)

