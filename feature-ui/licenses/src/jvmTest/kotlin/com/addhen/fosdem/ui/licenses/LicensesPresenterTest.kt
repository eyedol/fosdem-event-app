// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.core.api.screens.LicensesScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import com.addhen.fosdem.data.licenses.api.repository.LicensesRepository
import com.addhen.fosdem.data.licenses.repository.mapper.toLicense
import com.addhen.fosdem.model.api.licenses.License
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.TestResourceUtil
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class LicensesPresenterTest {
  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(LicensesScreen)
  private val fakeLicenseApi = HappyPathLicensesApi(coroutineTestRule.testDispatcherProvider)
  private val expectedLicenses = fakeLicenseApi.getLicenses()
    .map { it.toLicense() }
    .groupBy { it.groupId }
    .map { (groupId, artifacts) ->
      LicenseGroup(
        id = groupId,
        artifacts = artifacts.sortedBy { it.artifactId },
      )
    }
    .sortedBy { it.id }
  private val repository = FakeLicensesDataRepository(fakeLicenseApi)
  private val sut = LicensesPresenter(navigator, repository)

  @Test
  fun `should show licenses`() = coroutineTestRule.runTest {
    sut.test {
      val actualLoadingItem = awaitItem()

      assertEquals(emptyList<LicenseGroup>(), actualLoadingItem.licenses)

      val actualLoadedLicenses = awaitItem()

      assertEquals(expectedLicenses, actualLoadedLicenses.licenses)
    }
  }

  @Test
  fun `should error when fetching licenses`() = coroutineTestRule.runTest {
    val fakeLicenseApi = UnhappyPathLicensesApi(coroutineTestRule.testDispatcherProvider)
    val repository = FakeLicensesDataRepository(fakeLicenseApi)
    val sut = LicensesPresenter(navigator, repository)

    sut.test {
      assertEquals("Unhappy path", awaitError().message)
    }
  }

  @Test
  fun `given user navigates to GoToLink event is emitted to navigate user to GoToLink`() =
    coroutineTestRule.runTest {
      val goToLink = LicensesUiEvent.GoToLink("https://example.com")

      sut.test {
        awaitItem() // Loading

        val licenseUiState = awaitItem() // Loaded licenses

        licenseUiState.eventSink(goToLink)

        assertEquals(UrlScreen(goToLink.url), navigator.awaitNextScreen())
      }
    }

  @Test
  fun `given users navigates up event is emitted to pop the screen`() =
    coroutineTestRule.runTest {
      val navigateUp = LicensesUiEvent.NavigateUp

      val sut = LicensesPresenter(navigator, repository)

      sut.test {
        awaitItem() // Loading

        val licenseUiState = awaitItem() // Loaded licenses

        licenseUiState.eventSink(navigateUp)
        expectNoEvents()
      }
    }

  class FakeLicensesDataRepository(
    private val licensesApi: LicensesApi,
  ) : LicensesRepository {
    override suspend fun getLicenses(): List<License> {
      return licensesApi.fetchLicenses().map { it.toLicense() }
    }
  }

  @OptIn(ExperimentalSerializationApi::class)
  class HappyPathLicensesApi(
    private val dispatchers: AppCoroutineDispatchers,
  ) : LicensesApi {

    private val licenseJSON = TestResourceUtil.readLicensesJson()
    private val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }

    override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
      getLicenses()
    }

    fun getLicenses(): List<LicenseDto> {
      return json.decodeFromString<List<LicenseDto>>(licenseJSON)
    }
  }

  internal class UnhappyPathLicensesApi(
    private val dispatchers: AppCoroutineDispatchers,
  ) : LicensesApi {
    override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
      throw RuntimeException("Unhappy path")
    }
  }
}
