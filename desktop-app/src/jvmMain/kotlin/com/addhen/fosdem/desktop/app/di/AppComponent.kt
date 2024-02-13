// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app.di

import com.addhen.fosdem.core.api.ApplicationInfo
import com.addhen.fosdem.core.api.Flavor
import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.core.api.di.CoreApiBinds
import com.addhen.fosdem.data.core.api.di.CoreDataApiBinds
import com.addhen.fosdem.data.events.di.EventsDataBinds
import com.addhen.fosdem.data.licenses.di.LicencesDataBinds
import com.addhen.fosdem.data.rooms.di.RoomsDataBinds
import com.addhen.fosdem.data.sqldelight.database.di.SqlDelightDatabaseComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

interface DataComponent :
  CoreApiBinds,
  CoreDataApiBinds,
  EventsDataBinds,
  LicencesDataBinds,
  RoomsDataBinds,
  SqlDelightDatabaseComponent

@Component
@ApplicationScope
abstract class AppComponent : DataComponent {

  @ApplicationScope
  @Provides
  fun provideApplicationId(): ApplicationInfo = ApplicationInfo(
    packageName = "com.addhen.fosdem.desktop",
    debugBuild = true,
    flavor = Flavor.Prod,
    versionName = "1.0.0",
    versionCode = 1,
  )
  companion object
}
