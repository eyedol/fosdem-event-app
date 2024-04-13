// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

import com.addhen.fosdem.core.api.ApplicationInfo
import com.addhen.fosdem.core.api.Flavor
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AboutPresenterTest {

  private val applicationInfo = ApplicationInfo(
    packageName = "com.addhen.fosdem",
    debugBuild = false,
    Flavor.Prod,
    versionName = "1.0.0",
    versionCode = 1,
  )

  private val sut = AboutPresenter(
    navigator = FakeNavigator(AboutScreen),
    applicationInfo = applicationInfo,
  )

  @Test
  fun `about screen should display correct version name`() = runTest {
    val expectedVersionName = "${applicationInfo.versionName}(${applicationInfo.versionCode})"

    sut.test {
      val actualAboutUiState = awaitItem()
      assertEquals(actualAboutUiState.versionName, expectedVersionName)
    }
  }
}
