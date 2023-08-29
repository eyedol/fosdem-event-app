// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Constants
import com.addhen.fosdem.data.sqldelight.api.SqlDriverFactory

class AndroidSqlDriverFactory : SqlDriverFactory {
  lateinit var appContext: Context

  override fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(
      Database.Schema,
      appContext,
      Constants.DB_NAME,
      callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
        override fun onOpen(db: SupportSQLiteDatabase) {
          db.execSQL("PRAGMA foreign_keys = ON;")
        }
      },
    )
  }
}
