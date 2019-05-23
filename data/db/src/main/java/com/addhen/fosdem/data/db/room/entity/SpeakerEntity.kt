package com.addhen.fosdem.data.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speaker")
data class SpeakerEntity(
    @PrimaryKey val id: Long,
    val name: String
)

