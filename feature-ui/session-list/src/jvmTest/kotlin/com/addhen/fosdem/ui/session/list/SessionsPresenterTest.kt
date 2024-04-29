// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.list.component.SessionsSheetUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
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
  fun `should successfully show session list`() = coroutineTestRule.runTest {
    givenEventList()
    val expectedSessionUiStateList = SessionsSheetUiState.ListSession(
      days = dayTabs,
      sessionListUiStates = fakeRepository.events().groupAndMapEventsWithDays(),
    )

    sut.test {
      val actualLoadingSessionUiState = awaitItem()

      val actualSessionUiStateList = awaitItem()

      assertEquals(
        SessionsSheetUiState.Loading(dayTabs),
        actualLoadingSessionUiState.content,
      )
      assertEquals(expectedSessionUiStateList, actualSessionUiStateList.content)
    }
  }

  private fun givenEventList() {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    fakeRepository.addEvents(*events.toTypedArray())
  }

  private fun List<Event>.groupAndMapEventsWithDays(): PersistentMap<DayTab, SessionListUiState> {
    val groupEventsByDay = groupBy { it.day }
    val sessionsWithDays = mutableMapOf<DayTab, SessionListUiState>()
    groupEventsByDay.asSequence().forEach { (key, events) ->
      val sortedAndGroupEvents = events.sortAndGroupedEventsItems()
      sessionsWithDays[key.toDayTab()] = SessionListUiState(sortedAndGroupEvents)
    }
    return sessionsWithDays.toPersistentMap()
  }
}
