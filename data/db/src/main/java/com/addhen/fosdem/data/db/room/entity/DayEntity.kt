package com.addhen.fosdem.data.db.room.entity

import androidx.room.ColumnInfo

data class DayEntity(
    @ColumnInfo(name = "day_index") val index: Int,
    @ColumnInfo(name = "day_date") val date: Long
)
