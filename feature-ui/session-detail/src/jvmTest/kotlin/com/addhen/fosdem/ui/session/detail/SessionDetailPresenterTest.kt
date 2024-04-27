// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SessionDetailPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(SessionDetailScreen(1))
  private val fakeRepository = FakeEventsRepository()

  private val repository: Lazy<EventsRepository>
    get() = lazy { fakeRepository }

  private val sut = SessionDetailPresenter(SessionDetailScreen(1), navigator, repository)

  @AfterEach
  fun tearDown() {
    fakeRepository.clearEvents()
    fakeRepository.shouldCauseAnError.set(false)
  }

  @Test
  fun `should successfully load session detail`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
    val expectedSessionDetailScreenUiStateLoaded = SessionDetailScreenUiState.Loaded(
      sessionDetailUiState = SessionDetailItemSectionUiState(
        event = day1Event,
      ),
    )
    fakeRepository.addEvents(*events.toTypedArray())

    sut.test {
      val actualSessionDetailScreenUiStateLoading = awaitItem()
      val actualSessionDetailScreenUiStateLoaded = awaitItem()

      assertEquals(
        expectedSessionDetailScreenUiStateLoading,
        actualSessionDetailScreenUiStateLoading.sessionDetailScreenUiState,
      )

      assertEquals(
        expectedSessionDetailScreenUiStateLoaded,
        actualSessionDetailScreenUiStateLoaded.sessionDetailScreenUiState,
      )
    }
  }

  @Test
  fun `should fail to load session detail`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
    fakeRepository.addEvents(*events.toTypedArray())
    fakeRepository.shouldCauseAnError.set(true)

    sut.test {
      val actualSessionDetailScreenUiStateLoading = awaitItem()
      val actualErrorUiState = awaitItem()
      assertEquals(
        expectedSessionDetailScreenUiStateLoading,
        actualSessionDetailScreenUiStateLoading.sessionDetailScreenUiState,
      )
      assertEquals(
        "Error occurred while getting event with id: 1",
        actualErrorUiState.message?.message,
      )
      assertEquals("Try again", actualErrorUiState.message?.actionLabel)
    }
  }

  @Test
  fun `should fail to load session detail and clear ui message`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
    fakeRepository.addEvents(*events.toTypedArray())
    fakeRepository.shouldCauseAnError.set(true)

    sut.test {
      val actualSessionDetailScreenUiStateLoading = awaitItem()
      val actualErrorUiState = awaitItem()
      assertEquals(
        expectedSessionDetailScreenUiStateLoading,
        actualSessionDetailScreenUiStateLoading.sessionDetailScreenUiState,
      )
      assertEquals(
        "Error occurred while getting event with id: 1",
        actualErrorUiState.message?.message,
      )
      assertEquals("Try again", actualErrorUiState.message?.actionLabel)

      actualSessionDetailScreenUiStateLoading.eventSink(
        SessionDetailUiEvent.ClearMessage(2),
      )
      expectNoEvents()
    }
  }

  @Test
  fun `should navigate to open a link when a link is clicked`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
    val expectedSessionDetailScreenUiStateLoaded = SessionDetailScreenUiState.Loaded(
      sessionDetailUiState = SessionDetailItemSectionUiState(
        event = day1Event,
      ),
    )
    val expectedUrl = "https://fosdem.org"
    val expectedSessionDetailUiEvent = SessionDetailUiEvent.ShowLink(expectedUrl)
    fakeRepository.addEvents(*events.toTypedArray())

    sut.test {
      val actualSessionDetailScreenUiStateLoading = awaitItem()
      val actualSessionDetailScreenUiStateLoaded = awaitItem()

      actualSessionDetailScreenUiStateLoading.eventSink(expectedSessionDetailUiEvent)

      assertEquals(
        expectedSessionDetailScreenUiStateLoading,
        actualSessionDetailScreenUiStateLoading.sessionDetailScreenUiState,
      )

      assertEquals(
        expectedSessionDetailScreenUiStateLoaded,
        actualSessionDetailScreenUiStateLoaded.sessionDetailScreenUiState,
      )
      assertEquals(UrlScreen(expectedUrl), navigator.awaitNextScreen())
    }
  }
}
