// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import com.addhen.fosdem.core.api.screens.CalendarScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.ShareScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.core.api.timeZoneBrussels
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.descriptionFullText
import com.addhen.fosdem.model.api.endAtLocalDateTime
import com.addhen.fosdem.model.api.startAtLocalDateTime
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.toInstant
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
    val (expectedSessionDetailScreenUiStateLoading, expectedSessionDetailScreenUiStateLoaded) =
      givenSessionDetailScreenUiState()

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
  fun `successfully load an event and navigate to open a link when a link is clicked`() =
    coroutineTestRule.runTest {
      val (expectedSessionDetailScreenUiStateLoading, expectedSessionDetailScreenUiStateLoaded) =
        givenSessionDetailScreenUiState()
      val expectedUrl = "https://fosdem.org"
      val expectedSessionDetailUiEvent = SessionDetailUiEvent.ShowLink(expectedUrl)

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

  @Test
  fun `successfully load an event and navigate to share screen to share an event`() =
    coroutineTestRule.runTest {
      val (expectedSessionDetailScreenUiStateLoading, expectedSessionDetailScreenUiStateLoaded) =
        givenSessionDetailScreenUiState()
      val expectedText =
        """
                <br>|Title: ${day1Event.title}</br>
                <br>|Schedule: ${day1Event.day.date}: ${day1Event.startAt} - ${day1Event.endAt}</br>
                <br>|Room: ${day1Event.room.name}</br>
                <br>|Speaker: ${day1Event.speakers.joinToString { speaker -> speaker.name }}</br>
                <br>|Url: ${day1Event.url}</br>
                <br>|---</br>
                |Description: ${day1Event.descriptionFullText}
        """.trimMargin()
      val expectedSessionDetailUiEvent = SessionDetailUiEvent.ShareSession(day1Event)

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
        assertEquals(ShareScreen(expectedText), navigator.awaitNextScreen())
      }
    }

  @Test
  fun `successfully load an event and navigate to calendar screen`() =
    coroutineTestRule.runTest {
      val (expectedSessionDetailScreenUiStateLoading, expectedSessionDetailScreenUiStateLoaded) =
        givenSessionDetailScreenUiState()
      val text =
        """
              <br>|Speaker: ${day1Event.speakers.joinToString { speaker -> speaker.name }}</br>
              <br>|Url: ${day1Event.url}</br>
              <br>|---</br>
              |Description: ${day1Event.descriptionFullText}
        """.trimIndent()
      val expectedSessionDetailUiEvent = SessionDetailUiEvent.RegisterSessionToCalendar(day1Event)

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
        assertEquals(
          CalendarScreen(
            day1Event.title,
            day1Event.room.name,
            text,
            day1Event.startAtLocalDateTime.toInstant(timeZoneBrussels).toEpochMilliseconds(),
            day1Event.endAtLocalDateTime.toInstant(timeZoneBrussels).toEpochMilliseconds(),
          ),
          navigator.awaitNextScreen(),
        )
      }
    }

  @Test
  fun `successfully load an event and toggle a session as bookmarked`() =
    coroutineTestRule.runTest {
      val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
      val expectedBookmarkedEvent = day1Event.copy(isBookmarked = true)
      val expectedSessionDetailScreenUiStateLoading = SessionDetailScreenUiState.Loading
      val expectedSessionDetailScreenUiStateLoaded = SessionDetailScreenUiState.Loaded(
        sessionDetailUiState = SessionDetailItemSectionUiState(
          event = day1Event,
        ),
      )
      val expectedSessionDetailUiEvent = SessionDetailUiEvent.ToggleSessionBookmark(day1Event.id)
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
        assertEquals(
          expectedBookmarkedEvent,
          fakeRepository.events().first { it.id == day1Event.id },
        )
      }
    }

  @Test
  fun `successfully load an event and fail to toggle a session as bookmarked`() =
    coroutineTestRule.runTest {
      val expectedBookmarkedEvent = day1Event.copy(isBookmarked = false)
      val (
        expectedSessionDetailScreenUiStateLoading,
        expectedSessionDetailScreenUiStateLoaded,
      ) = givenSessionDetailScreenUiState()
      val expectedSessionDetailUiEvent = SessionDetailUiEvent.ToggleSessionBookmark(day1Event.id)

      sut.test {
        val actualSessionDetailScreenUiStateLoading = awaitItem()
        val actualSessionDetailScreenUiStateLoaded = awaitItem()

        fakeRepository.shouldCauseAnError.set(true)
        actualSessionDetailScreenUiStateLoading.eventSink(expectedSessionDetailUiEvent)
        val actualErrorUiState = awaitItem()

        assertEquals(
          expectedSessionDetailScreenUiStateLoading,
          actualSessionDetailScreenUiStateLoading.sessionDetailScreenUiState,
        )

        assertEquals(
          expectedSessionDetailScreenUiStateLoaded,
          actualSessionDetailScreenUiStateLoaded.sessionDetailScreenUiState,
        )
        assertEquals(
          "Error occurred while toggling bookmark with event id ${day1Event.id}",
          actualErrorUiState.message?.message,
        )
        assertEquals(
          expectedBookmarkedEvent,
          fakeRepository.events().first { it.id == day1Event.id },
        )
      }
    }

  @Test
  fun `successfully load an event and navigate to previous screen`() = coroutineTestRule.runTest {
    val (
      expectedSessionDetailScreenUiStateLoading,
      expectedSessionDetailScreenUiStateLoaded,
    ) = givenSessionDetailScreenUiState()

    val expectedSessionDetailUiEvent = SessionDetailUiEvent.GoToSessionList
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
      expectNoEvents()
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
