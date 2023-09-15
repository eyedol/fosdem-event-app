// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.addhen.fosdem.android.app.di.ActivityComponent
import com.addhen.fosdem.android.app.di.AppComponent
import com.addhen.fosdem.android.app.di.UiComponent
import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.main.MainContent
import com.addhen.fosdem.ui.main.MainScreen
import com.addhen.fosdem.ui.main.component.MainNavigationItem
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val component = MainActivityComponent::class.create(this)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val backstack = rememberSaveableBackStack { push(MainScreen) }
      val navigator = rememberCircuitNavigator(backstack)

      component.mainContent(
        backstack,
        navigator,
        Modifier,
      )
    }
  }

  fun buildNavigationItems(strings: TiviStrings): List<HomeNavigationItem> {
    return listOf(
      HomeNavigationItem(
        screen = DiscoverScreen,
        label = strings.discoverTitle,
        contentDescription = strings.cdDiscoverTitle,
        iconImageVector = Icons.Outlined.Weekend,
        selectedImageVector = Icons.Default.Weekend,
      ),
      MainNavigationItem(
        screen = UpNextScreen,
        label = strings.upnextTitle,
        contentDescription = strings.cdUpnextTitle,
        iconImageVector = Icons.Default.Subscriptions,
      ),
      MainNavigationItem(
        screen = LibraryScreen,
        label = strings.libraryTitle,
        contentDescription = strings.cdLibraryTitle,
        iconImageVector = Icons.Outlined.VideoLibrary,
        selectedImageVector = Icons.Default.VideoLibrary,
      ),
      MainNavigationItem(
        screen = SearchScreen,
        label = strings.searchNavigationTitle,
        contentDescription = strings.cdSearchNavigationTitle,
        iconImageVector = Icons.Default.Search,
      ),
    )
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
