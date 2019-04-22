package com.addhen.fosdem.data.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link")
data class LinkEntity(
    @PrimaryKey val id: Int,
    val href: String,
    val text: String
)
