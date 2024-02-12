package com.addhen.fosdem.data.licenses.di

import com.addhen.fosdem.data.licenses.api.repository.LicensesRepository
import com.addhen.fosdem.data.licenses.repository.LicensesDataRepository
import me.tatarka.inject.annotations.Provides

expect interface LicencesApiPlatformComponent

interface LicencesDataBinds : LicencesApiPlatformComponent {

  @Provides
  fun providesLicensesRepository(bind: LicensesDataRepository): LicensesRepository = bind
}
