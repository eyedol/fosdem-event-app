// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.addhen.fosdem.data.sqldelight.api.Constants
import com.addhen.fosdem.data.sqldelight.api.SqlDriverFactory

class IosSqlDelightDriverFactory : SqlDriverFactory {

  override fun createDriver(): SqlDriver {
    return NativeSqliteDriver(DestructiveMigrationSchema, Constants.DB_NAME)
  }
}
