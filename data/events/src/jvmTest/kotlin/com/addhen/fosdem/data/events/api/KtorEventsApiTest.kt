// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.data.core.api.AppError
import com.addhen.fosdem.data.events.createKtorEventsApiWithError
import com.addhen.fosdem.data.events.createKtorEventsApiWithEvents
import com.addhen.fosdem.test.CoroutineTestRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension

class KtorEventsApiTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: KtorEventsApi

  @Test
  fun `successfully fetches and deserializes to event dto`() = coroutineTestRule.runTest {
    sut = createKtorEventsApiWithEvents(coroutineTestRule.testDispatcherProvider)

    val actual = sut.fetchEvents()

    assertEquals(1, actual.days.size)
    assertEquals(1, actual.days.first().rooms.size)
    assertEquals(2, actual.days.first().rooms.first().events.size)
  }

  @Test
  fun `throws AppError#UnknownException as fetching schedules there is a malformed xml`() {
    val escapedDollarSign = "\$"
    val expectedErrorMessage = """
      com.addhen.fosdem.data.core.api.AppError${escapedDollarSign}UnknownException: nl.adaptivity.xmlutil.XmlException: 1:7 - Unexpected START_DOCUMENT in state START_DOC
    """.trimIndent()
    sut = createKtorEventsApiWithError(coroutineTestRule.testDispatcherProvider)

    val actual = assertThrows<AppError.UnknownException> {
      coroutineTestRule.runTest { sut.fetchEvents() }
    }

    assertEquals(AppError.UnknownException::class, actual::class)
    assertEquals(expectedErrorMessage, actual.message)
  }
}
