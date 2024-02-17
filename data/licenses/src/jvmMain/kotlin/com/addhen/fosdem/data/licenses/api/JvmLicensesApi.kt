// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
class JvmLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    emptyList()
  }
}
