/*
 * Copyright 2022 Addhen Limited
 */
package com.findreels.data.sqldelight.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.findreels.data.sqldelight.api.database.Constants
import com.findreels.data.sqldelight.api.database.DestructiveMigrationSchema
import com.findreels.data.sqldelight.api.database.SqlDriverFactory
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidSqlDelightDriverFactory @Inject constructor(
  @ApplicationContext private val appContext: Context,
) : SqlDriverFactory {

  override fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(
      DestructiveMigrationSchema,
      appContext,
      Constants.DB_NAME,
      callback = object : AndroidSqliteDriver.Callback(DestructiveMigrationSchema) {
        override fun onOpen(db: SupportSQLiteDatabase) {
          db.execSQL("PRAGMA foreign_keys=ON;")
        }
      },
    )
  }
}
