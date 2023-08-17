/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.GenreEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieGenreAdapter : ColumnAdapter<List<GenreEntity>, String> {

  override fun decode(databaseValue: String): List<GenreEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<GenreEntity>): String = Json.encodeToString(value)
}
