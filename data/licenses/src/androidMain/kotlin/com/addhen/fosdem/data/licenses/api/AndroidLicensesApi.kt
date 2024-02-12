// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api

import android.app.Application
import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidLicensesApi(
  private val context: Application,
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {

  @ExperimentalSerializationApi
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }

    json.decodeFromStream(context.assets.open("licenses.json"))
  }
}
