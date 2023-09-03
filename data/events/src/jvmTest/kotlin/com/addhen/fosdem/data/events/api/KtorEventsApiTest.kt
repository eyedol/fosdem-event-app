// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.data.core.api.AppError
import com.addhen.fosdem.data.core.api.onError
import com.addhen.fosdem.data.core.api.onSuccess
import com.addhen.fosdem.data.events.createKtorEventsApiWithError
import com.addhen.fosdem.data.events.createKtorEventsApiWithEvents
import com.addhen.fosdem.test.CoroutineTestRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class KtorEventsApiTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: KtorEventsApi

  @Test
  fun `successfully fetches and deserializes to event dto`() = coroutineTestRule.runTest {
    sut = createKtorEventsApiWithEvents(coroutineTestRule.testDispatcherProvider)

    sut.fetchEvents()
      .onSuccess { actual ->
        assertEquals(1, actual.days.size)
        assertEquals(1, actual.days.first().rooms.size)
        assertEquals(2, actual.days.first().rooms.first().events.size)
      }
  }

  @Test
  fun `throws AppError#UnknownExceptio as fetching schedules there is a malformed xml`() =
    coroutineTestRule.runTest {
      sut = createKtorEventsApiWithError(coroutineTestRule.testDispatcherProvider)

      sut.fetchEvents().onError {
        assertTrue(it is AppError.UnknownException)
      }
    }
}
