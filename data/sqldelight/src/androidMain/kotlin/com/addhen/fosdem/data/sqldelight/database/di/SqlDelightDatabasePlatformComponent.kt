// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database.di

import app.cash.sqldelight.db.SqlDriver
import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.sqldelight.database.AndroidSqlDriverFactory
import me.tatarka.inject.annotations.Provides

actual interface SqlDelightDatabasePlatformComponent {

  @Provides
  @ApplicationScope
  fun provideDriverFactory(): SqlDriver = AndroidSqlDriverFactory().createDriver()
}
