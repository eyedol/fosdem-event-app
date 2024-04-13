// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.network

import com.addhen.fosdem.data.core.api.toAppError
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML

class ApiService(val url: String, val httpClient: HttpClient) {

  suspend inline fun <reified T : Any> get(
    dispatcher: CoroutineDispatcher,
    crossinline block: HttpRequestBuilder.() -> Unit = {},
  ): T = makeApiCall(dispatcher) {
    httpClient.get(url, block).asType<T>()
  }

  suspend inline fun <reified T : Any> HttpResponse.asType(): T {
    val xmlText = bodyAsText()
    val serializer = serializer<T>()
    return DEFAULT_XML.decodeFromString(serializer, xmlText)
  }

  suspend inline fun <reified T> makeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline apiCall: suspend () -> T,
  ): T = withContext(dispatcher) {
    try {
      apiCall.invoke()
    } catch (e: Throwable) {
      coroutineContext.ensureActive()
      throw e.toAppError()
    }
  }

  companion object {

    val DEFAULT_XML: XML = XML {
      repairNamespaces = true
      xmlDeclMode = XmlDeclMode.None
      indentString = ""
      autoPolymorphic = false
      defaultPolicy { ignoreUnknownChildren() }
      this.xmlDeclMode
    }
  }
}
