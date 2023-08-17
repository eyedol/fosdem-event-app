// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime

internal object LocalTimeColumnAdapter : ColumnAdapter<LocalTime, String> {
  override fun decode(databaseValue: String): LocalTime = databaseValue.toLocalTime()
  override fun encode(value: LocalTime): String = value.toString()
}
