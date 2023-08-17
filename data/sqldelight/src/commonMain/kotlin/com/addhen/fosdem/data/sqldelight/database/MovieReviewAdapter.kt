/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.entities.ReviewEntity
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MovieReviewAdapter : ColumnAdapter<List<ReviewEntity>, String> {

  override fun decode(databaseValue: String): List<ReviewEntity> =
    Json.decodeFromString(databaseValue)

  override fun encode(value: List<ReviewEntity>): String = Json.encodeToString(value)
}
