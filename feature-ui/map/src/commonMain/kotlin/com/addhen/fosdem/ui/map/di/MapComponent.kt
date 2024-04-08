// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.map.MapUiFactory
import com.addhen.fosdem.ui.map.MapUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface MapComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindMapUiPresenterFactory(
    factory: MapUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindMapUiFactory(
    factory: MapUiFactory,
  ): Ui.Factory = factory
}
