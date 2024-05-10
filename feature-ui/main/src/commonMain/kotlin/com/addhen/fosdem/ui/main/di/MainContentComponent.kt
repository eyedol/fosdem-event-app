// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

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
