// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.licenses.LicensesUiFactory
import com.addhen.fosdem.ui.licenses.LicensesUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface LicensesComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindLicensesUiPresenterFactory(
    factory: LicensesUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindLicensesUiFactoryFactory(
    factory: LicensesUiFactory,
  ): Ui.Factory = factory
}
