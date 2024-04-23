// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.Track
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@Suppress("CAST_NEVER_SUCCEEDS")
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
  fun `should show all bookmarked events with 2nd day selected`() = coroutineTestRule.runTest {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    val expectedBookmarkedSessions = SessionBookmarkSheetUiState.ListBookmark(
      listOf(day2Event3).sortAndGroupedEventsItems(),
      isDayFirstSelected = false,
      isDaySecondSelected = true,
    )
    fakeRepository.addEvents(*events.toTypedArray())
    sut.test {
      val actualLoadingSessionUiState = awaitItem()

      actualLoadingSessionUiState.eventSink(SessionBookmarkUiEvent.FilterSecondDayBookmarks)

      val actualSessionUiState = awaitItem()

      assertEquals(SessionBookmarkSheetUiState.Loading(), actualLoadingSessionUiState.content)
      assertEquals(expectedBookmarkedSessions, actualSessionUiState.content)
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
    }
  }

  class FakeEventsRepository : EventsRepository {

    private val events = mutableListOf<Event>()

    private val tracks = mutableListOf<Track>()
    override fun getEvents(): Flow<List<Event>> = flow {
      emit(events)
    }

    override fun getEvents(date: LocalDate): Flow<List<Event>> = flow {
      val event = events.filter { it.day.date == date }
      emit(event)
    }

    override fun getAllBookmarkedEvents(): Flow<List<Event>> = flow {
      emit(events.filter { it.isBookmarked })
    }

    override fun getEvent(id: Long): Flow<Event> = flow {
      emit(events.first { it.id == id })
    }

    override fun getTracks(): Flow<List<Track>> = flow {
      emit(tracks)
    }

    override suspend fun toggleBookmark(id: Long): Result<Unit> {
      val event = events.first { it.id == id }
      events.replaceAll { event.copy(isBookmarked = event.isBookmarked.not()) }
      return Result.success(Unit)
    }

    override suspend fun refresh(): Result<Unit> {
      return Result.success(Unit)
    }

    fun addEvents(vararg newEvents: Event) {
      events.addAll(newEvents)
    }

    fun clearEvents() {
      events.clear()
    }
  }
}
