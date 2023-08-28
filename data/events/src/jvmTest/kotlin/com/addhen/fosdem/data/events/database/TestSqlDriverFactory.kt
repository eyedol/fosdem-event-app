// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.sqldelight.logs.LogSqliteDriver
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.SqlDriverFactory

object TestSqlDriverFactory : SqlDriverFactory {
  override fun createDriver(): SqlDriver {
    return LogSqliteDriver(sqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { driver ->
      Database.Schema.create(driver)
    }
    ) { log ->
      println(log)
    }
  }
}
