package com.addhen.fosdem.ui.main.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.main.AppContent
import com.addhen.fosdem.ui.main.MainContent
import me.tatarka.inject.annotations.Provides

interface MainContentComponent {
  @Provides
  @ActivityScope
  fun bindTiviContent(mainContent: MainContent): AppContent = mainContent
}
