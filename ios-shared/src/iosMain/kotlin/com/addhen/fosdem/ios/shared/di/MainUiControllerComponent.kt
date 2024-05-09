// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ios.shared.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.main.MainUiViewController
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class MainUiControllerComponent(
  @Component val appComponent: AppComponent,
) : UiComponent {

  abstract val uiViewControllerFactory: () -> UIViewController

  @Provides
  @ActivityScope
  fun uiViewController(bind: MainUiViewController): UIViewController = bind()

  companion object
}
