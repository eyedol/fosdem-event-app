// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalTime

internal object LocalTimeColumnAdapter : ColumnAdapter<LocalTime, String> {
  override fun decode(databaseValue: String): LocalTime = databaseValue.let { LocalTime.parse(it) }

  override fun encode(value: LocalTime): String = value.toString()
}
