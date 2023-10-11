// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.database

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.addhen.fosdem.data.events.database.TestSqlDriverFactory
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Days
import com.addhen.fosdem.data.sqldelight.api.Events
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class DatabaseTest {
  private lateinit var sqlDriver: SqlDriver

  lateinit var database: Database

  @BeforeEach
  fun setUp() {
    sqlDriver = TestSqlDriverFactory.createDriver()
    database = Database(
      driver = sqlDriver,
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

  @AfterEach
  fun tearDown() {
    sqlDriver.close()
  }
}

internal object LocalDateColumnAdapter : ColumnAdapter<LocalDate, String> {
  override fun decode(databaseValue: String): LocalDate = databaseValue.toLocalDate()
  override fun encode(value: LocalDate): String = value.toString()
}

internal object LocalTimeColumnAdapter : ColumnAdapter<LocalTime, String> {
  override fun decode(databaseValue: String): LocalTime = databaseValue.toLocalTime()
  override fun encode(value: LocalTime): String = value.toString()
}
