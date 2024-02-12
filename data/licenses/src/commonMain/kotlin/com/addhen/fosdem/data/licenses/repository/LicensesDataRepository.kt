// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.core.api.toAppError
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.repository.LicensesRepository
import com.addhen.fosdem.data.licenses.repository.mapper.toLicense
import com.addhen.fosdem.model.api.licenses.License
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.ensureActive
import me.tatarka.inject.annotations.Inject

@Inject
class LicensesDataRepository(
  private val licensesApi: LicensesApi,
) : LicensesRepository {

  override suspend fun getLicense(): AppResult<List<License>> {
    return try {
      val licenses = licensesApi.fetchLicenses()
      AppResult.Success(licenses.map { it.toLicense() })
    } catch (e: Throwable) {
      coroutineContext.ensureActive()
      AppResult.Error(e.toAppError())
    }
  }
}
