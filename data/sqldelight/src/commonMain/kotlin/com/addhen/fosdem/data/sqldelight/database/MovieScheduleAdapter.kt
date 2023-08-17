/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.ScheduleEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieScheduleAdapter : ColumnAdapter<List<ScheduleEntity>, String> {

  override fun decode(databaseValue: String): List<ScheduleEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<ScheduleEntity>): String = Json.encodeToString(value)
}
