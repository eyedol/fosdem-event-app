// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.test

import com.addhen.fosdem.data.core.api.network.KtorHttpClientApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

fun createHttpClient(
  response: String,
  statusCode: HttpStatusCode = HttpStatusCode.OK,
  contentType: String = "application/xml",
): HttpClient {
  val mockEngine = MockEngine { _ ->
    respond(
      content = ByteReadChannel(response),
      status = statusCode,
      headers = headersOf(HttpHeaders.ContentType, contentType),
    )
  }
  return KtorHttpClientApi.create(engineFactory = createClientEngineFactory(mockEngine))
}

private fun createClientEngineFactory(mockEngine: MockEngine) = object :
  HttpClientEngineFactory<MockEngineConfig> {
  override fun create(block: MockEngineConfig.() -> Unit): HttpClientEngine {
    return mockEngine
  }
}
