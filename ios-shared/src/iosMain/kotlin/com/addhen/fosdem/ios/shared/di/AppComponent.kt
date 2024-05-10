// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ios.shared.di

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
import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

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

  @OptIn(ExperimentalNativeApi::class)
  @ApplicationScope
  @Provides
  fun provideApplicationId(): ApplicationInfo = ApplicationInfo(
    packageName = NSBundle.mainBundle.bundleIdentifier ?: error("No bundle ID found"),
    debugBuild = Platform.isDebugBinary,
    flavor = if (Platform.isDebugBinary) Flavor.Devel else Flavor.Prod,
    versionName = NSBundle.mainBundle.infoDictionary
      ?.get("CFBundleShortVersionString") as? String
      ?: "",
    versionCode = (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)
      ?.toIntOrNull()
      ?: 0,
  )
  companion object
}
