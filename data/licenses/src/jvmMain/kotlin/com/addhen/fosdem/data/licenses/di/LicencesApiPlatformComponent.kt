// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.di

import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.licenses.api.JvmLicensesApi
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import me.tatarka.inject.annotations.Provides

actual interface LicencesApiPlatformComponent {

  @ApplicationScope
  @Provides
  fun bindLicensesApi(bind: JvmLicensesApi): LicensesApi = bind
}
