// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api

import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import com.addhen.fosdem.data.sample.HappyPathLicensesApi
import com.addhen.fosdem.test.CoroutineTestRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class JvmLicensesApiTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  @Test
  fun `getLicenses returns a list of licenses`() = coroutineTestRule.runTest {
    val expectedLicenses = HappyPathLicensesApi(
      coroutineTestRule.testDispatcherProvider,
    ).getLicenses()
    val sut = JvmLicensesApi(coroutineTestRule.testDispatcherProvider)

    val actualLicenses = sut.fetchLicenses()

    assertEquals(194, actualLicenses.size)
    assertEquals(expectedLicenses, actualLicenses)
  }

  @Test
  fun `getLicenses returns an empty list if there are no licenses`() = coroutineTestRule.runTest {
    // Create a fake JvmLicensesApi object
    val api = FakeJvmLicensesApi()

    // Set the fake licenses to return an empty list
    api.licenses = emptyList()

    // Call the getLicenses() method and assert that it returns an empty list
    val licenses = api.fetchLicenses()
    assertEquals(0, licenses.size)
  }
}

// Fake implementation of the JvmLicensesApi
class FakeJvmLicensesApi : LicensesApi {

  // Mutable list to store the fake licenses
  var licenses: List<LicenseDto> = emptyList()

  override suspend fun fetchLicenses(): List<LicenseDto> {
    return licenses
  }
}
