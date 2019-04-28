package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo

data class TrackEntity(
    @ColumnInfo(name = "track_id")
    val id: Long,
    @ColumnInfo(name = "track_name")
    val name: String,
    @ColumnInfo(name = "track_type")
    val type: String
)

