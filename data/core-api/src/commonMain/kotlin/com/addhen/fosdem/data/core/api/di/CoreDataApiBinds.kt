package com.addhen.fosdem.data.core.api.di

import com.addhen.fosdem.data.core.api.network.ApiService
import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides

private const val baseUrl = "https://fosdem.org"

interface CoreDataApiBinds : OkHttpPlatformComponent, CoreDataApiComponent
expect interface OkHttpPlatformComponent
interface CoreDataApiComponent {

  @Provides
  fun providesApiService(
    httpClient: HttpClient,
  ): ApiService = ApiService(baseUrl, httpClient)
}
