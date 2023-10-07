// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Application
import com.addhen.fosdem.android.app.di.AppComponent
import com.addhen.fosdem.android.app.di.create

class App : Application() {
  val component: AppComponent by lazy {
    AppComponent.create(this)
  }
}
