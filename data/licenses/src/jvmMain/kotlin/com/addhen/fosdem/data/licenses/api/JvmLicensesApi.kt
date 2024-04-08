// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.tatarka.inject.annotations.Inject

@Inject
class JvmLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {
  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }
    val inputStream = this::class.java.getResourceAsStream("/licenses.json")
      ?: return@withContext emptyList()

    json.decodeFromStream(inputStream)
  }
}
