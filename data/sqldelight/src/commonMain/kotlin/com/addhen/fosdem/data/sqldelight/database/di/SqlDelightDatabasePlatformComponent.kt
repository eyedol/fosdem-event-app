// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.database.di

import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.sqldelight.Database
import com.addhen.fosdem.data.sqldelight.database.DatabaseFactory
import me.tatarka.inject.annotations.Provides

expect interface SqlDelightDatabasePlatformComponent

interface SqlDelightDatabaseComponent : SqlDelightDatabasePlatformComponent {
  @ApplicationScope
  @Provides
  fun provideSqlDelightDatabase(
    factory: DatabaseFactory,
  ): Database = factory.build()
}
