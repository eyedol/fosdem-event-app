// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Application
import android.os.Build
import android.os.StrictMode
import com.addhen.fosdem.android.app.di.AppComponent
import com.addhen.fosdem.android.app.di.create

class App : Application() {
  val component: AppComponent by lazy {
    AppComponent.create(this)
  }

  override fun onCreate() {
    super.onCreate()
    setupStrictMode()
  }

  private fun setupStrictMode() {
    StrictMode.setThreadPolicy(
      StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build(),
    )
    StrictMode.setVmPolicy(
      StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectActivityLeaks()
        .detectLeakedClosableObjects()
        .detectLeakedRegistrationObjects()
        .detectFileUriExposure()
        .detectCleartextNetwork()
        .apply {
          if (Build.VERSION.SDK_INT >= 26) {
            detectContentUriWithoutPermission()
          }
          if (Build.VERSION.SDK_INT >= 29) {
            detectCredentialProtectedWhileLocked()
          }
          if (Build.VERSION.SDK_INT >= 31) {
            detectIncorrectContextUse()
            detectUnsafeIntentLaunch()
          }
        }
        .penaltyLog()
        .build(),
    )
  }
}
