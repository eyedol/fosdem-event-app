package com.addhen.fosdem.data.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speaker")
data class SpeakerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val bio: String?,
    val imageUrl: String?
)

