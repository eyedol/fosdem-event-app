// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app.di

import com.addhen.fosdem.core.api.di.ActivityScope
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class ActivityComponent(
  @Component val appComponent: AppComponent,
) /*: UiComponent {

  abstract val mainContent: MainContent
  companion object
}*/
