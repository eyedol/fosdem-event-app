// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api.api

import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto

interface LicensesApi {
  suspend fun fetchLicenses(): List<LicenseDto>
}
