// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.about.AboutUiFactory
import com.addhen.fosdem.ui.about.AboutUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface AboutComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindAboutUiPresenterFactory(
    factory: AboutUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindAboutUiFactoryFactory(
    factory: AboutUiFactory,
  ): Ui.Factory = factory
}
