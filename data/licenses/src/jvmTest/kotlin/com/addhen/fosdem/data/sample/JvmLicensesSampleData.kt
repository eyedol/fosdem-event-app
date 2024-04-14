// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sample

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import com.addhen.fosdem.test.TestResourceUtil
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class HappyPathLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {

  private val licenseJSON = TestResourceUtil.readLicensesJson()

  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    getLicenses()
  }

  fun getLicenses(): List<LicenseDto> {
    return Json.decodeFromString<List<LicenseDto>>(licenseJSON)
  }
}

internal class UnhappyPathLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    throw RuntimeException("Unhappy path")
  }
}
