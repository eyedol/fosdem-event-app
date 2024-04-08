// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.desktop.app.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.main.MainContent
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
  @Component val appComponent: AppComponent,
) : UiComponent {

  abstract val mainContent: MainContent
  companion object
}
