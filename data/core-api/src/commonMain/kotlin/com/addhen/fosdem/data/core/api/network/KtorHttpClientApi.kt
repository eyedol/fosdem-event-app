// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import co.touchlab.kermit.Logger as KermitLogger

object KtorHttpClientApi {

  fun <T> create(
    engineFactory: HttpClientEngineFactory<T>,
    block: T.() -> Unit = {},
  ): HttpClient where T : HttpClientEngineConfig =
    HttpClient(engineFactory) {
      engine(block)
      install(Logging) {
        logger = object : Logger {
          override fun log(message: String) {
            KermitLogger.d { message }
          }
        }
        level = LogLevel.ALL
      }
    }
}
