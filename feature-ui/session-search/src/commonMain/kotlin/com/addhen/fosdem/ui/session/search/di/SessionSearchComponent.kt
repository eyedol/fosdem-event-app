// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.session.search.SessionSearchUiFactory
import com.addhen.fosdem.ui.session.search.SessionSearchUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SessionSearchComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionSearchPresenterFactory(
    factory: SessionSearchUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionSearchFactoryFactory(
    factory: SessionSearchUiFactory,
  ): Ui.Factory = factory
}
