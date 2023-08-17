/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.CastEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieCastAdapter : ColumnAdapter<List<CastEntity>, String> {

  override fun decode(databaseValue: String): List<CastEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<CastEntity>): String = Json.encodeToString(value)
}
