// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.di

import com.addhen.fosdem.core.api.di.ApplicationScope
import com.addhen.fosdem.data.core.api.network.KtorHttpClientApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import me.tatarka.inject.annotations.Provides
import okhttp3.Interceptor

actual interface HttpClientPlatformComponent {

  @ApplicationScope
  @Provides
  fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
    return listOf()
  }

  @Provides
  @ApplicationScope
  fun provideHttpClient(
    networkInterceptors: List<@JvmSuppressWildcards Interceptor>,
  ): HttpClient {
    return KtorHttpClientApi.create(
      engineFactory = OkHttp,
    ) {
      networkInterceptors.forEach { addNetworkInterceptor(it) }
    }
  }
}
