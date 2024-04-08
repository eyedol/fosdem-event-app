// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.repository

import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.repository.LicensesRepository
import com.addhen.fosdem.data.licenses.repository.mapper.toLicense
import com.addhen.fosdem.model.api.licenses.License
import me.tatarka.inject.annotations.Inject

@Inject
class LicensesDataRepository(
  private val licensesApi: LicensesApi,
) : LicensesRepository {

  override suspend fun getLicenses(): List<License> {
    return licensesApi.fetchLicenses().map { it.toLicense() }
  }
}
