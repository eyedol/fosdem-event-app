// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.about.di.AboutComponent
import com.addhen.fosdem.ui.licenses.di.LicensesComponent
import com.addhen.fosdem.ui.map.di.MapComponent
import com.addhen.fosdem.ui.session.bookmark.di.SessionBookmarkComponent
import com.addhen.fosdem.ui.session.detail.di.SessionDetailComponent
import com.addhen.fosdem.ui.session.list.di.SessionComponent
import com.addhen.fosdem.ui.session.search.di.SessionSearchComponent
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface UiComponent :
  SessionComponent,
  SessionDetailComponent,
  SessionBookmarkComponent,
  SessionSearchComponent,
  MapComponent,
  LicensesComponent,
  AboutComponent {

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
