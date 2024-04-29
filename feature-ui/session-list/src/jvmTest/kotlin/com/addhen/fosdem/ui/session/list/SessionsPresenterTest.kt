// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
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
  fun tearDown() = fakeRepository.clearEvents()

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

  @Test
  fun `should successfully load and show an empty session`() = coroutineTestRule.runTest {
    val expectedSessionUiStateEmpty = SessionsSheetUiState.Empty(days = dayTabs)

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiStateEmpty = awaitItem()

      assertEquals(
        SessionsSheetUiState.Loading(dayTabs),
        actualLoadingSessionUiState.content,
      )
      assertEquals(expectedSessionUiStateEmpty, actualSessionUiStateEmpty.content)
    }
  }

  @Test
  fun `should fail to load sessions and show an error`() = coroutineTestRule.runTest {
    givenEventList()
    fakeRepository.shouldCauseAnError.set(true)
    val expectedSessionUiStateError = SessionsSheetUiState.Loading(days = dayTabs)

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiStateError = awaitItem()

      assertEquals(
        SessionsSheetUiState.Loading(dayTabs),
        actualLoadingSessionUiState.content,
      )
      assertEquals(expectedSessionUiStateError, actualSessionUiStateError.content)
      assertEquals(
        "Error occurred while getting events",
        actualSessionUiStateError.message?.message,
      )
    }
  }

  @Test
  fun `should show all sessions and navigate to session details screen`() =
    coroutineTestRule.runTest {
      givenEventList()
      val expectedSessionUiStateList = SessionsSheetUiState.ListSession(
        days = dayTabs,
        sessionListUiStates = fakeRepository.events().groupAndMapEventsWithDays(),
      )
      val sessionDetailsScreen = SessionDetailScreen(day2Event3.id)

      sut.test {
        val actualLoadingSessionUiState = awaitItem()
        val actualSessionUiState = awaitItem()

        actualSessionUiState.eventSink(SessionUiEvent.GoToSessionDetails(day2Event3.id))

        assertEquals(
          SessionsSheetUiState.Loading(dayTabs),
          actualLoadingSessionUiState.content,
        )
        assertEquals(expectedSessionUiStateList, actualSessionUiState.content)
        assertEquals(sessionDetailsScreen, navigator.awaitNextScreen())
        expectNoEvents()
      }
    }

  @Test
  fun `should successfully toggle event as bookmarked`() = coroutineTestRule.runTest {
    givenEventList()
    val expectedBookmarkedEvent = day1Event.copy(isBookmarked = true)
    val expectedSessionUiStateList = SessionsSheetUiState.ListSession(
      days = dayTabs,
      sessionListUiStates = fakeRepository.events().groupAndMapEventsWithDays(),
    )

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()
      actualSessionUiState.eventSink(SessionUiEvent.ToggleSessionBookmark(day1Event.id))

      expectNoEvents()
      ensureAllEventsConsumed()
      assertEquals(SessionsSheetUiState.Loading(dayTabs), actualLoadingSessionUiState.content)
      assertEquals(expectedSessionUiStateList, actualSessionUiState.content)
      assertEquals(expectedBookmarkedEvent, fakeRepository.events().first { it.id == day1Event.id })
    }
  }

  @Test
  fun `should fail to bookmark event and show an error`() = coroutineTestRule.runTest {
    givenEventList()
    val expectedSessionUiStateList = SessionsSheetUiState.ListSession(
      days = dayTabs,
      sessionListUiStates = fakeRepository.events().groupAndMapEventsWithDays(),
    )

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()
      fakeRepository.shouldCauseAnError.set(true)
      actualSessionUiState.eventSink(SessionUiEvent.ToggleSessionBookmark(day1Event.id))

      val actualSessionUiStateError = awaitItem()

      assertEquals(
        SessionsSheetUiState.Loading(dayTabs),
        actualLoadingSessionUiState.content,
      )
      assertEquals(expectedSessionUiStateList, actualSessionUiState.content)
      assertEquals(
        "Error occurred while toggling bookmark with event id: ${day1Event.id}",
        actualSessionUiStateError.message?.message,
      )
    }
  }

  @Test
  fun `should show all sessions and navigate to session bookmarks screen`() =
    coroutineTestRule.runTest {
      givenEventList()
      val expectedSessionUiStateList = SessionsSheetUiState.ListSession(
        days = dayTabs,
        sessionListUiStates = fakeRepository.events().groupAndMapEventsWithDays(),
      )
      val sessionBookmarksScreen = SessionBookmarkScreen

      sut.test {
        val actualLoadingSessionUiState = awaitItem()
        val actualSessionUiState = awaitItem()

        actualSessionUiState.eventSink(SessionUiEvent.GoToBookmarkSessions)

        assertEquals(
          SessionsSheetUiState.Loading(dayTabs),
          actualLoadingSessionUiState.content,
        )
        assertEquals(expectedSessionUiStateList, actualSessionUiState.content)
        assertEquals(sessionBookmarksScreen, navigator.awaitNextScreen())
        expectNoEvents()
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
