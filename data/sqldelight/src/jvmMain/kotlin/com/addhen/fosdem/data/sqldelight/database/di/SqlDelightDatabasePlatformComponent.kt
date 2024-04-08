// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.api.Constants
import me.tatarka.inject.annotations.Provides
import java.io.File

actual interface SqlDelightDatabasePlatformComponent {

  @Provides
  @ApplicationScope
  fun provideDriverFactory(): SqlDriver = JdbcSqliteDriver(
    url = "jdbc:sqlite:${getDatabaseFile().absolutePath}",
  ).also { db ->
    Database.Schema.create(db)
    db.execute(null, "PRAGMA foreign_keys=ON", 0)
  }

  private fun getDatabaseFile(): File {
    return File(
      appDir.also { if (!it.exists()) it.mkdirs() },
      Constants.DB_NAME,
    )
  }

  private val appDir: File
    get() {
      val os = System.getProperty("os.name").lowercase()
      return when {
        os.contains("win") -> {
          File(System.getenv("AppData"), "fosdem/db")
        }

        os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
          File(System.getProperty("user.home"), ".fosdem")
        }

        os.contains("mac") -> {
          File(System.getProperty("user.home"), "Library/Application Support/fosdem")
        }

        else -> error("Unsupported operating system")
      }
    }
}
