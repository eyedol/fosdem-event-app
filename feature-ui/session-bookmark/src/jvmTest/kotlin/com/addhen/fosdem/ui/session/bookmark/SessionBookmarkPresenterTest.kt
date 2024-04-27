// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SessionBookmarkPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val fakeNavigator = FakeNavigator(SessionBookmarkScreen)
  private val fakeRepository = FakeEventsRepository()

  private val repository: Lazy<EventsRepository>
    get() = lazy { fakeRepository }

  private val sut = SessionBookmarkPresenter(fakeNavigator, repository)

  @AfterEach
  fun tearDown() {
    fakeRepository.clearEvents()
  }

  @Test
  fun `should show all bookmarked events with no day selected`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
      listOf(
        day2Event3,
      ).sortAndGroupedEventsItems(),
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
    fakeRepository.addEvents(*events.toTypedArray())
    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()

      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
    }
  }

  @Test
  fun `should show all 2nd day bookmarked events when 2nd day is selected`() =
    coroutineTestRule.runTest {
      val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
      val expectedAllBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(day2Event3).sortAndGroupedEventsItems(),
        isDayFirstSelected = false,
        isDaySecondSelected = false,
      )
      val expectedSecondDayBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(day2Event3).sortAndGroupedEventsItems(),
        isDayFirstSelected = false,
        isDaySecondSelected = true,
      )
      fakeRepository.addEvents(*events.toTypedArray())

      sut.test {
        val actualLoadingSessionUiState = awaitItem() // Loading
        val actualAllBookmarkedSessionUiState = awaitItem() // Bookmark list
        actualAllBookmarkedSessionUiState.eventSink(SessionBookmarkUiEvent.FilterSecondDayBookmarks)
        awaitItem() // Not sure what this extra event is
        val actualSecondDayBookmarkedSessions = awaitItem()

        assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
        assertEquals(expectedAllBookmarkedSessions, actualAllBookmarkedSessionUiState.content)
        assertEquals(expectedSecondDayBookmarkedSessions, actualSecondDayBookmarkedSessions.content)
      }
    }

  @Test
  fun `should show all 1st day bookmarked events when 1st day is selected`() =
    coroutineTestRule.runTest {
      val day1EventBookmarked = day1Event.copy(isBookmarked = true)
      val day1Event2Bookmarked = day1Event2.copy(isBookmarked = true)
      val events = listOf(
        day1EventBookmarked,
        day1Event2Bookmarked,
        day2Event1,
        day2Event2,
        day2Event3,
      )
      val expectedAllBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(day1EventBookmarked, day1Event2Bookmarked, day2Event3).sortAndGroupedEventsItems(),
        isDayFirstSelected = false,
        isDaySecondSelected = false,
      )
      val expectedFirstDayBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(day1EventBookmarked, day1Event2Bookmarked).sortAndGroupedEventsItems(),
        isDayFirstSelected = true,
        isDaySecondSelected = false,
      )
      fakeRepository.addEvents(*events.toTypedArray())

      sut.test {
        val actualLoadingSessionUiState = awaitItem() // Loading
        val actualAllBookmarkedSessionUiState = awaitItem() // Bookmark list
        actualAllBookmarkedSessionUiState.eventSink(SessionBookmarkUiEvent.FilterFirstDayBookmarks)
        awaitItem() // Not sure what this extra event is
        val actualFirstDayBookmarkedSessions = awaitItem()

        assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
        assertEquals(expectedAllBookmarkedSessions, actualAllBookmarkedSessionUiState.content)
        assertEquals(expectedFirstDayBookmarkedSessions, actualFirstDayBookmarkedSessions.content)
      }
    }

  @Test
  fun `should show empty bookmark list`() = coroutineTestRule.runTest {
    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()

      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(
        SessionBookmarkSheetUiState.Empty(isDayFirstSelected = false, isDaySecondSelected = false),
        actualSessionUiState.content,
      )
      ensureAllEventsConsumed()
    }
  }

  @Test
  fun `should successfully toggle event as bookmarked`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedBookmarkedEvent = day1Event.copy(isBookmarked = true)
    val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
      listOf(day2Event3).sortAndGroupedEventsItems(),
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
    fakeRepository.addEvents(*events.toTypedArray())

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()
      actualSessionUiState.eventSink(
        SessionBookmarkUiEvent.ToggleSessionBookmark(day1Event.id),
      )

      expectNoEvents()
      ensureAllEventsConsumed()
      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
      assertEquals(expectedBookmarkedEvent, fakeRepository.events().first { it.id == day1Event.id })
    }
  }

  @Test
  fun `should fail to toggle event as bookmarked`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedBookmarkedEvent = day1Event.copy(isBookmarked = false)
    val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
      listOf(day2Event3).sortAndGroupedEventsItems(),
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
    fakeRepository.addEvents(*events.toTypedArray())
    fakeRepository.shouldCauseAnError.set(true)

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()
      actualSessionUiState.eventSink(SessionBookmarkUiEvent.ToggleSessionBookmark(day1Event.id))
      val actualErrorUiState = awaitItem()

      ensureAllEventsConsumed()
      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
      assertEquals(
        "Error occurred while toggling bookmark with event id: ${day1Event.id}",
        actualErrorUiState.message?.message,
      )
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
      assertEquals(expectedBookmarkedEvent, fakeRepository.events().first { it.id == day1Event.id })
    }
  }

  @Test
  fun `should show all bookmarked events and navigate to session detail`() =
    coroutineTestRule.runTest {
      val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
      val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(
          day2Event3,
        ).sortAndGroupedEventsItems(),
        isDayFirstSelected = false,
        isDaySecondSelected = false,
      )
      fakeRepository.addEvents(*events.toTypedArray())
      val gotoSessionDetail = SessionBookmarkUiEvent.GoToSessionDetails(day2Event3.id)

      sut.test {
        val actualLoadingSessionUiState = awaitItem()
        val actualSessionUiState = awaitItem()

        actualSessionUiState.eventSink(gotoSessionDetail)

        assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
        assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
        assertEquals(SessionDetailScreen(day2Event3.id), fakeNavigator.awaitNextScreen())
      }
    }

  @Test
  fun `should show all bookmarked events and navigate to the previous screen`() =
    coroutineTestRule.runTest {
      val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
      val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
        listOf(
          day2Event3,
        ).sortAndGroupedEventsItems(),
        isDayFirstSelected = false,
        isDaySecondSelected = false,
      )
      fakeRepository.addEvents(*events.toTypedArray())
      val gotoPreviousScreen = SessionBookmarkUiEvent.GoToPreviousScreen

      sut.test {
        val actualLoadingSessionUiState = awaitItem()
        val actualSessionUiState = awaitItem()

        actualSessionUiState.eventSink(gotoPreviousScreen)

        assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
        assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
        expectNoEvents()
      }
    }

  @Test
  fun `should show all bookmarked events and clear ui message`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
      listOf(
        day2Event3,
      ).sortAndGroupedEventsItems(),
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
    fakeRepository.addEvents(*events.toTypedArray())
    fakeRepository.shouldCauseAnError.set(true)
    val clearMessage = SessionBookmarkUiEvent.ClearMessage

    sut.test {
      val actualLoadingSessionUiState = awaitItem()
      val actualSessionUiState = awaitItem()
      actualSessionUiState.eventSink(SessionBookmarkUiEvent.ToggleSessionBookmark(day1Event.id))
      val actualErrorUiState = awaitItem() // SessionBookmarkSheetUiState with error message
      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
      assertEquals(
        "Error occurred while toggling bookmark with event id: ${day1Event.id}",
        actualErrorUiState.message?.message,
      )

      actualSessionUiState.eventSink(clearMessage)

      val actualMessageClearedSessionUiState = awaitItem()

      assertEquals(actualMessageClearedSessionUiState.message, null)
      assertEquals(expectedBookmarkedSessions, actualMessageClearedSessionUiState.content)
      expectNoEvents()
    }
  }
}
