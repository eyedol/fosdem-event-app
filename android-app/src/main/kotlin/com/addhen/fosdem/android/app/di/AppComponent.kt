// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app.di

import android.app.Application
import android.content.Context
import com.addhen.fosdem.android.app.App
import com.addhen.fosdem.core.api.ApplicationInfo
import com.addhen.fosdem.core.api.Flavor
import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.core.api.di.CoreApiBinds
import com.addhen.fosdem.data.core.api.di.CoreDataApiBinds
import com.addhen.fosdem.data.events.di.EventsDataBinds
import com.addhen.fosdem.data.rooms.di.RoomsDataBinds
import com.addhen.fosdem.data.sqldelight.database.di.SqlDelightDatabaseComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

interface DataComponent :
  CoreApiBinds,
  CoreDataApiBinds,
  EventsDataBinds,
  RoomsDataBinds,
  SqlDelightDatabaseComponent

@Component
@ApplicationScope
abstract class AppComponent(
  @get:Provides val application: Application,
) : DataComponent {

  @Suppress("DEPRECATION")
  @ApplicationScope
  @Provides
  fun provideApplicationInfo(application: Application): ApplicationInfo {
    val packageInfo = application.packageManager.getPackageInfo(application.packageName, 0)

    return ApplicationInfo(
      packageName = application.packageName,
      debugBuild = false,
      flavor = Flavor.Prod,
      versionName = packageInfo.versionName,
      versionCode = packageInfo.versionCode,
    )
  }

  companion object {
    fun from(context: Context): AppComponent {
      return (context.applicationContext as App).component
    }
  }
}
