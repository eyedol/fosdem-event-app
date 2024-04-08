// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.session.bookmark.SessionBookmarkUiFactory
import com.addhen.fosdem.ui.session.bookmark.SessionBookmarkUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SessionBookmarkComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionBookmarkPresenterFactory(
    factory: SessionBookmarkUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionBookmarkFactoryFactory(
    factory: SessionBookmarkUiFactory,
  ): Ui.Factory = factory
}
