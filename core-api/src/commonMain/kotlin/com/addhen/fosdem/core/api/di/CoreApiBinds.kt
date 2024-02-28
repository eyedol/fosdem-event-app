// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.di

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import me.tatarka.inject.annotations.Provides

interface CoreApiBinds {

  @OptIn(ExperimentalCoroutinesApi::class)
  @ApplicationScope
  @Provides
  fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
    io = Dispatchers.IO,
    default = Dispatchers.Default,
    databaseRead = Dispatchers.IO.limitedParallelism(4),
    databaseWrite = Dispatchers.IO.limitedParallelism(1),
    computation = Dispatchers.Default,
    main = Dispatchers.Main,
  )
}
