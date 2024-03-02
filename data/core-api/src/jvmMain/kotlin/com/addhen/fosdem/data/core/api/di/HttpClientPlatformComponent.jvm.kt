// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.di

import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.core.api.network.KtorHttpClientApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit
import me.tatarka.inject.annotations.Provides
import okhttp3.ConnectionPool

actual interface HttpClientPlatformComponent {

  @Provides
  @ApplicationScope
  fun provideHttpClient(): HttpClient {
    return KtorHttpClientApi.create(
      engineFactory = OkHttp,
    ) {
      config {
        connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
      }
    }
  }
}
