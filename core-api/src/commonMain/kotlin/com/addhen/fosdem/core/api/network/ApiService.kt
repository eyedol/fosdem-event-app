// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api.network

import com.addhen.fosdem.core.api.toAppError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get

class ApiService(val url: String, val httpClient: HttpClient) {

  suspend inline fun <reified T : Any> get(block: HttpRequestBuilder.() -> Unit = {}): T = try {
    httpClient.get(url, block).body()
  } catch (e: Throwable) {
    throw e.toAppError()
  }
}
