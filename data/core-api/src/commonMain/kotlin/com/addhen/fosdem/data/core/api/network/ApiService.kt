// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML

class ApiService(val url: String, val httpClient: HttpClient) {

  val defaultXml: XML = XML {
    repairNamespaces = true
    xmlDeclMode = XmlDeclMode.None
    indentString = ""
    autoPolymorphic = false
    defaultPolicy { ignoreUnknownChildren() }
    this.xmlDeclMode
  }

  suspend inline fun <reified T : Any> get(block: HttpRequestBuilder.() -> Unit = {}): T = try {
    httpClient.get(url, block).asType<T>()
  } catch (e: Throwable) {
    if (e is CancellationException) throw e
    throw e.toAppError()
  }

  suspend inline fun <reified T : Any> HttpResponse.asType(): T {
    val xmlText = bodyAsText()
    val serializer = serializer<T>()
    return defaultXml.decodeFromString(serializer, xmlText)
  }
}
