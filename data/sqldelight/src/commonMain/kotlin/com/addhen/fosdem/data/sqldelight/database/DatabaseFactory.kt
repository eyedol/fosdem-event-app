// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.db.SqlDriver
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Days
import com.addhen.fosdem.data.sqldelight.api.Events
import me.tatarka.inject.annotations.Inject

@Inject
class DatabaseFactory(
  private val driver: SqlDriver,
) {
  fun build(): Database = Database(
    driver = driver,
    daysAdapter = Days.Adapter(
      dateAdapter = LocalDateColumnAdapter,
    ),
    eventsAdapter = Events.Adapter(
      start_timeAdapter = LocalTimeColumnAdapter,
      durationAdapter = LocalTimeColumnAdapter,
      dateAdapter = LocalDateColumnAdapter,
    ),
  )
}
