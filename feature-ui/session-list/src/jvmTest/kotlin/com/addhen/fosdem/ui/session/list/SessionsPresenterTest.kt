// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class SessionsPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(SessionsScreen)
  private val fakeRepository = FakeEventsRepository()

  private val repository: Lazy<EventsRepository>
    get() = lazy { fakeRepository }

  private val sut = SessionsPresenter(navigator, repository)

  @AfterEach
  fun tearDown() {
    fakeRepository.clearEvents()
    fakeRepository.shouldCauseAnError.set(false)
  }

  @Test
  fun `should show session list`() = coroutineTestRule.runTest {
    sut.test {
    }
  }

  private fun givenSessionDetailScreenUiState():
    Pair<SessionDetailScreenUiState.Loading, SessionDetailScreenUiState.Loaded> {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
    val expectedSessionDetailScreenUiStateLoaded = SessionDetailScreenUiState.Loaded(
      sessionDetailUiState = SessionDetailItemSectionUiState(
        event = day1Event,
      ),
    )
    fakeRepository.addEvents(*events.toTypedArray())
    return Pair(
      expectedSessionDetailScreenUiStateLoading,
      expectedSessionDetailScreenUiStateLoaded,
    )
  }
}
