// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.di

import com.addhen.fosdem.data.rooms.api.database.RoomsDao
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.data.rooms.database.RoomsDbDao
import com.addhen.fosdem.data.rooms.repository.RoomsDataRepository
import me.tatarka.inject.annotations.Provides

interface RoomsDataBinds {

  @Provides
  fun providesRoomsRepository(bind: RoomsDataRepository): RoomsRepository = bind

  @Provides
  fun providesRoomsDao(bind: RoomsDbDao): RoomsDao = bind
}
