// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
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
        { title, room, description, startAtMillSeconds, endAtMillSeconds ->
          launchCalendar(title, room, description, startAtMillSeconds, endAtMillSeconds)
        },
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
    val html = info.parseHtml()

    runCatching {
      ShareCompat.IntentBuilder(this@MainActivity)
        .setText(html)
        .setType("text/plain")
        .startChooser()
    }.onFailure {
      Logger.e(it) { "Failed to startActivity to share" }
    }
  }

  private fun launchCalendar(
    title: String,
    room: String,
    description: String,
    startAtMillSeconds: Long,
    endAtMillSeconds: Long,
  ) {
    val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
      data = CalendarContract.Events.CONTENT_URI
      putExtras(
        bundleOf(
          CalendarContract.Events.TITLE to "[$room] $title",
          CalendarContract.Events.DESCRIPTION to description,
          CalendarContract.Events.EVENT_LOCATION to room,
          CalendarContract.EXTRA_EVENT_BEGIN_TIME to startAtMillSeconds,
          CalendarContract.EXTRA_EVENT_END_TIME to endAtMillSeconds,
        ),
      )
    }

    runCatching {
      this@MainActivity.startActivity(calendarIntent)
    }.onFailure {
      Logger.e(it) { "Failed to startActivity to register event in the calendar" }
    }
  }

  private fun String.parseHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
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
