/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.MediaEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieMediaAdapter : ColumnAdapter<List<MediaEntity>, String> {

  override fun decode(databaseValue: String): List<MediaEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<MediaEntity>): String = Json.encodeToString(value)
}
