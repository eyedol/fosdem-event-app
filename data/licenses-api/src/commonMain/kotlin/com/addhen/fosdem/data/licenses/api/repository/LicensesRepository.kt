// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api.repository

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.model.api.licenses.License

interface LicensesRepository {

  suspend fun getLicense(): AppResult<List<License>>
}
