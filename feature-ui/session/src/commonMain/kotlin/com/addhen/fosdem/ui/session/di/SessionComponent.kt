// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.main.MainUiFactory
import com.addhen.fosdem.ui.main.MainUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SessionComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindMainPresenterFactory(factory: SessionUiPresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindMainUiFactoryFactory(factory: SessionUiFactory): Ui.Factory = factory
}
