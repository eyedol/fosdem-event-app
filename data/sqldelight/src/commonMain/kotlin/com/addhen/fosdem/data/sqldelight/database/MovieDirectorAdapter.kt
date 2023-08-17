/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.DirectorEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieDirectorAdapter : ColumnAdapter<List<DirectorEntity>, String> {

  override fun decode(databaseValue: String): List<DirectorEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<DirectorEntity>): String = Json.encodeToString(value)
}
