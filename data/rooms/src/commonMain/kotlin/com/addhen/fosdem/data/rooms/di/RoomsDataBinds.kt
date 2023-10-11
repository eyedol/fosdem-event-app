// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.rooms.di

import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.data.rooms.repository.RoomsDataRepository
import me.tatarka.inject.annotations.Provides

interface RoomsDataBinds {

  @Provides
  fun providesRoomsRepository(bind: RoomsDataRepository): RoomsRepository = bind
}
