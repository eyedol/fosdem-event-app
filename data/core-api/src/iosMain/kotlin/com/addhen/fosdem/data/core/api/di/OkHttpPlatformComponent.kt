// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.di

import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.core.api.network.KtorHttpClientApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import me.tatarka.inject.annotations.Provides

interface OkHttpPlatformComponent {

  @Provides
  @ApplicationScope
  fun provideHttpClient(): HttpClient {
    return KtorHttpClientApi.create(
      engineFactory = Darwin,
    ) {}
  }
}
