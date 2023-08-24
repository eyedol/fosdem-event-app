package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.core.api.network.ApiService
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class KtorEventsApiTest {

  private val api = ApiService()
  private val sut = KtorEventsApi()
  @BeforeTest
  fun setup() {

  }

  @AfterTest
  fun tearDown() {

  }

  @Test
  fun `successfully fetches and deserializes to eventdto`() = runBlocking {
    val mockEngine = MockEngine { _ ->
      respond(
        content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
      )
    }
  }
}
