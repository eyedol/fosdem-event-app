// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.addhen.fosdem.android.app.di.ActivityComponent
import com.addhen.fosdem.android.app.di.AppComponent
import com.addhen.fosdem.android.app.di.UiComponent
import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.ui.main.MainContent
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Navigation icon color can be changed since API 26(O)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      enableEdgeToEdge()
    } else {
      enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.auto(
          lightScrim = Color.Transparent.toArgb(),
          darkScrim = Color.Transparent.toArgb(),
        ),
        navigationBarStyle = SystemBarStyle.auto(
          lightScrim = Color.Transparent.toArgb(),
          darkScrim = Color.Transparent.toArgb(),
        ),
      )
    }

    val component = MainActivityComponent::class.create(this)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val backstack = rememberSaveableBackStack { push(SessionScreen) }
      val navigator = rememberCircuitNavigator(backstack)

      component.mainContent(
        backstack,
        navigator,
        Modifier,
      )
    }
  }
}

@ActivityScope
@Component
abstract class MainActivityComponent(
  @get:Provides override val activity: Activity,
  @Component val applicationComponent: AppComponent = AppComponent.from(activity),
) : ActivityComponent, UiComponent {
  abstract val mainContent: MainContent
}
