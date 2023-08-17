/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import com.findreels.data.sqldelight.api.database.Constants
import com.findreels.data.sqldelight.api.database.DestructiveMigrationSchema
import com.findreels.data.sqldelight.api.database.SqlDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

class IosSqlDelightDriverFactory : SqlDriverFactory {

  override fun createDriver(): SqlDriver {
    return NativeSqliteDriver(DestructiveMigrationSchema, Constants.DB_NAME)
  }
}
