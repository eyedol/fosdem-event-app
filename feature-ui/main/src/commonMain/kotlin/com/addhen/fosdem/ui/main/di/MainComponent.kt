// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package competency.school.ui.main.di

import com.addhen.fosdem.ui.main.MainUiFactory
import com.addhen.fosdem.ui.main.MainUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import competency.school.core.api.di.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface MainComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindMainPresenterFactory(factory: MainUiPresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindMainUiFactoryFactory(factory: MainUiFactory): Ui.Factory = factory
}
