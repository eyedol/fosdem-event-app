// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.view.WindowCompat
import co.touchlab.kermit.Logger
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

    val component = MainActivityComponent.create(this)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val backstack = rememberSaveableBackStack(listOf(SessionScreen))
      val navigator = rememberCircuitNavigator(backstack)

      component.mainContent(
        backstack,
        navigator,
        { url -> launchUrl(url) },
        { info -> shareInfo(info) },
        Modifier,
      )
    }
  }

  private fun launchUrl(url: String) {
    if (url.isBlank()) return

    val intent = CustomTabsIntent.Builder().build()
    intent.launchUrl(this@MainActivity, url.toUri())
  }

  private fun shareInfo(info: String) {
    if (info.isEmpty()) return
    val html = HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    try {
      ShareCompat.IntentBuilder(this@MainActivity)
        .setText(html)
        .setType("text/plain")
        .startChooser()
    } catch (e: ActivityNotFoundException) {
      Logger.e(e) { "ActivityNotFoundException Fail startActivity" }
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

  companion object
}
