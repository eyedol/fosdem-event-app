package com.addhen.fosdem.data.licenses.api

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

class JvmLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    emptyList()
  }
}
