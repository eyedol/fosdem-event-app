// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.data.licenses.repository.LicensesDataRepository
import com.addhen.fosdem.data.licenses.repository.mapper.toLicense
import com.addhen.fosdem.data.sample.HappyPathLicensesApi
import com.addhen.fosdem.data.sample.UnhappyPathLicensesApi
import com.addhen.fosdem.test.CoroutineTestRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class LicensesDataRepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var licensesDataRepository: LicensesDataRepository

  @Test
  fun `retrieves licenses successfully`() = coroutineTestRule.runTest {
    val fake = HappyPathLicensesApi(coroutineTestRule.testDispatcherProvider)
    val expectedLicenses = fake.getLicenses().map { it.toLicense() }
    licensesDataRepository = LicensesDataRepository(fake)
    val actual = licensesDataRepository.getLicenses()

    assertEquals(expectedLicenses, actual)
  }

  @Test
  fun `retrieves licenses with error`() = coroutineTestRule.runTest {
    licensesDataRepository = LicensesDataRepository(
      UnhappyPathLicensesApi(coroutineTestRule.testDispatcherProvider),
    )

    try {
      licensesDataRepository.getLicenses()
    } catch (e: Exception) {
      assertEquals(RuntimeException::class, e::class)
    }
  }
}
