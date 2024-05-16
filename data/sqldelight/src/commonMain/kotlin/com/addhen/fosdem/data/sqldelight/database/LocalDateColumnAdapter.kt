// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

internal object LocalDateColumnAdapter : ColumnAdapter<LocalDate, String> {
  override fun decode(databaseValue: String): LocalDate = databaseValue.let { LocalDate.parse(it) }

  override fun encode(value: LocalDate): String = value.toString()
}
