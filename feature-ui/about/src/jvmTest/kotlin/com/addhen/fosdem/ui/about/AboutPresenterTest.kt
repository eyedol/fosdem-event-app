// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

import com.addhen.fosdem.core.api.ApplicationInfo
import com.addhen.fosdem.core.api.Flavor
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.core.api.screens.LicensesScreen
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
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

  private val navigator = FakeNavigator(AboutScreen)

  private val sut = AboutPresenter(
    navigator = navigator,
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

  @Test
  fun `given user navigate to Privacy Policy screen emits event to go to privacy policy screen`() =
    runTest {
      val expectedPPLink = "https://addhen.com/privacy-policy"
      val expectedAboutItem = AboutUiEvent.GoToAboutItem(
        AboutItem.PrivacyPolicy(expectedPPLink),
      )

      sut.test {
        val actualAboutUiState = awaitItem()

        actualAboutUiState.eventSink(expectedAboutItem)

        assertEquals(UrlScreen(expectedPPLink), navigator.awaitNextScreen())
      }
    }

  @Test
  fun `given user navigate to Licenses screen emits event to go to license screen`() = runTest {
    val expectedAboutItem = AboutUiEvent.GoToAboutItem(
      AboutItem.License,
    )

    sut.test {
      val actualAboutUiState = awaitItem()

      actualAboutUiState.eventSink(expectedAboutItem)

      assertEquals(LicensesScreen, navigator.awaitNextScreen())
    }
  }

  @Test
  fun `given user navigate to Link Screen emits event to go to link screen`() = runTest {
    val expectedLink = "https://addhen.com"
    val expectedAboutItem = AboutUiEvent.GoToLink(expectedLink)

    sut.test {
      val actualAboutUiState = awaitItem()

      actualAboutUiState.eventSink(expectedAboutItem)

      assertEquals(UrlScreen(expectedLink), navigator.awaitNextScreen())
    }
  }

  @Test
  fun `given user navigate to go to Session Screen emits event to go to session screen`() =
    runTest {
      val expectedAboutItem = AboutUiEvent.GoToSession

      sut.test {
        val actualAboutUiState = awaitItem()

        actualAboutUiState.eventSink(expectedAboutItem)

        assertEquals(SessionsScreen, navigator.awaitNextScreen())
      }
    }
}
