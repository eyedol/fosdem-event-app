// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.session.detail.di.SessionDetailComponent
import com.addhen.fosdem.ui.session.di.SessionComponent
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface UiComponent : SessionComponent, SessionDetailComponent {

  @Provides
  @ActivityScope
  fun provideCircuitConfig(
    uiFactories: Set<Ui.Factory>,
    presenterFactories: Set<Presenter.Factory>,
  ): Circuit = Circuit.Builder()
    .addUiFactories(uiFactories)
    .addPresenterFactories(presenterFactories)
    .build()
}
