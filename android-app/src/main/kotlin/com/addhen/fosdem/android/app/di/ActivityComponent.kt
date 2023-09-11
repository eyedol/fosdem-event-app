// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app.di

import android.app.Activity
import com.addhen.fosdem.core.api.di.ActivityScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

interface ActivityComponent {

  @get:Provides
  val activity: Activity
}
