// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.session.list.SessionUiFactory
import com.addhen.fosdem.ui.session.list.SessionUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SessionComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionPresenterFactory(factory: SessionUiPresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionFactoryFactory(factory: SessionUiFactory): Ui.Factory = factory
}
