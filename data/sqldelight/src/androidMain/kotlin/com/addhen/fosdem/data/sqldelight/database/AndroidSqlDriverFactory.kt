// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import android.app.Application
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Constants
import com.addhen.fosdem.data.sqldelight.api.SqlDriverFactory

class AndroidSqlDriverFactory(private val application: Application) : SqlDriverFactory {

  override fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(
      Database.Schema,
      application,
      Constants.DB_NAME,
      callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
        override fun onOpen(db: SupportSQLiteDatabase) {
          db.execSQL("PRAGMA foreign_keys = ON;")
        }
      },
    )
  }
}
