// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Constants
import com.addhen.fosdem.data.sqldelight.api.SqlDriverFactory

class IosSqlDriverFactory : SqlDriverFactory {
  override fun createDriver(): SqlDriver =
    NativeSqliteDriver(
      Database.Schema,
      Constants.DB_NAME,
      maxReaderConnections = 4,
    )
}
