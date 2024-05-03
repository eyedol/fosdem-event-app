// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.Track
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.search.component.SearchFilterUiState
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalCoroutinesApi::class)
class SessionSearchPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(SessionsScreen)
  private val fakeRepository = FakeEventsRepository()
  private val fakeRoomsRepository = FakeRoomsRepository()

  private val eventsRepository: Lazy<EventsRepository>
    get() = lazy { fakeRepository }

  private val roomRepository: Lazy<RoomsRepository>
    get() = lazy { fakeRoomsRepository }

  private val sut: SessionSearchPresenter = SessionSearchPresenter(
    navigator = navigator,
    roomsRepository = roomRepository,
    eventsRepository = eventsRepository,
  )

  @AfterEach
  fun tearDown() {
    fakeRepository.clearEvents()
    fakeRoomsRepository.clearRooms()
  }

  @Test
  fun `should load events, tracks and its associated rooms for search`() =
    coroutineTestRule.runTest {
      givenEventListAndRoomsAndTracks()
      val expectedSearchSessionLoading = SearchUiState.Loading()
      val expectedSessionSearchList = SearchUiState.ListSearch(
        sessionItemMap = fakeRepository.events().sortAndGroupedEventsItems(),
        query = SearchQuery(""),
        filterDayUiState = SearchFilterUiState(
          items = dayTabs,
        ),
        filterRoomUiState = SearchFilterUiState(
          items = fakeRoomsRepository.rooms().map { FilterRoom(it.id, it.name) }.toImmutableList(),
        ),
        filterTrackUiState = SearchFilterUiState(
          items = fakeRepository.tracks().map { FilterTrack(it.name, it.type) }.toImmutableList(),
        ),
      )

      sut.test {
        val actualSearchSessionLoading = awaitItem()
        val actualSessionSearchUiState = awaitItem()

        assertEquals(expectedSearchSessionLoading, actualSearchSessionLoading.content)
        assertEquals(expectedSessionSearchList, actualSessionSearchUiState.content)
      }
    }

  @Test
  fun `should load empty events, tracks and its associated rooms for search`() =
    coroutineTestRule.runTest {
      val expectedSearchSessionLoading = SearchUiState.Loading()
      val expectedSessionSearchList = SearchUiState.Empty(
        query = SearchQuery(""),
        filterDayUiState = SearchFilterUiState(
          items = dayTabs,
        ),
        filterRoomUiState = SearchFilterUiState(
          items = fakeRoomsRepository.rooms().map { FilterRoom(it.id, it.name) }.toImmutableList(),
        ),
        filterTrackUiState = SearchFilterUiState(
          items = fakeRepository.tracks().map { FilterTrack(it.name, it.type) }.toImmutableList(),
        ),
      )

      sut.test {
        val actualSearchSessionLoading = awaitItem()
        val actualSessionSearchUiState = awaitItem()

        assertEquals(expectedSearchSessionLoading, actualSearchSessionLoading.content)
        assertEquals(expectedSessionSearchList, actualSessionSearchUiState.content)
      }
    }

  @Test
  fun `should filter events list by day`() =
    coroutineTestRule.runTest {
      givenEventListAndRoomsAndTracks()
      val events = listOf(day2Event1, day2Event2, day2Event3)
      val day2Tab = dayTabs[1]
      val expectedSearchSessionLoading = SearchUiState.Loading()
      val expectedSessionSearchList = SearchUiState.ListSearch(
        sessionItemMap = fakeRepository.events().sortAndGroupedEventsItems(),
        query = SearchQuery(""),
        filterDayUiState = SearchFilterUiState(
          items = dayTabs,
        ),
        filterRoomUiState = SearchFilterUiState(
          items = fakeRoomsRepository.rooms().map { FilterRoom(it.id, it.name) }.toImmutableList(),
        ),
        filterTrackUiState = SearchFilterUiState(
          items = fakeRepository.tracks().map { FilterTrack(it.name, it.type) }.toImmutableList(),
        ),
      )

      val expectedSearchListFiltered = expectedSessionSearchList.copy(
        sessionItemMap = events.sortAndGroupedEventsItems(),
        filterDayUiState = SearchFilterUiState(
          selectedItems = listOf(day2Tab).toPersistentList(),
          selectedValues = day2Tab.title,
          items = dayTabs,
        ),
      )

      sut.test {
        val actualSearchSessionLoading = awaitItem()
        val actualSessionSearchUiState = awaitItem()

        actualSessionSearchUiState.eventSink(
          SessionSearchUiEvent.FilterDay(day2Tab, isSelected = true),
        )

        // I don't understand why this emission occurred
        val actualSessionSearchListFiltered1 = awaitItem()
        // The emission that occurred as a result of the filter event
        val actualSessionSearchListFiltered = awaitItem()

        assertEquals(expectedSearchSessionLoading, actualSearchSessionLoading.content)
        assertEquals(expectedSessionSearchList, actualSessionSearchUiState.content)
        assertEquals(expectedSessionSearchList, actualSessionSearchListFiltered1.content)
        assertEquals(expectedSearchListFiltered, actualSessionSearchListFiltered.content)
      }
    }

  @Test
  fun `should filter events by title`() =
    coroutineTestRule.runTest {
      givenEventListAndRoomsAndTracks()
      val events = listOf(day2Event2)
      val searchTerm = "Making the world a better place through Open Source"
      val expectedSearchSessionLoading = SearchUiState.Loading()
      val expectedSessionSearchList = SearchUiState.ListSearch(
        sessionItemMap = fakeRepository.events().sortAndGroupedEventsItems(),
        query = SearchQuery(""),
        filterDayUiState = SearchFilterUiState(
          items = dayTabs,
        ),
        filterRoomUiState = SearchFilterUiState(
          items = fakeRoomsRepository.rooms().map { FilterRoom(it.id, it.name) }.toImmutableList(),
        ),
        filterTrackUiState = SearchFilterUiState(
          items = fakeRepository.tracks().map { FilterTrack(it.name, it.type) }.toImmutableList(),
        ),
      )

      val expectedSearchListFiltered = expectedSessionSearchList.copy(
        sessionItemMap = events.sortAndGroupedEventsItems(),
        query = SearchQuery(searchTerm),
      )

      sut.test {
        val actualSearchSessionLoading = awaitItem()
        val actualSessionSearchUiState = awaitItem()

        actualSessionSearchUiState.eventSink(
          SessionSearchUiEvent.QuerySearch(searchTerm),
        )

        val actualSessionSearchListFiltered = expectMostRecentItem()

        val actualSearchList = (actualSessionSearchListFiltered.content as SearchUiState.ListSearch)
          .sessionItemMap["2024-02-04T10:002024-02-04T10:50"]

        assertEquals(expectedSearchSessionLoading, actualSearchSessionLoading.content)
        assertEquals(expectedSessionSearchList, actualSessionSearchUiState.content)
        assertEquals(expectedSearchListFiltered, actualSessionSearchListFiltered.content)
        assertTrue(actualSearchList?.size == 1)
        assertTrue(actualSearchList?.first()?.title?.contains(searchTerm) ?: false)
      }
    }

  private fun givenEventListAndRoomsAndTracks() {
    val events = listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
    fakeRepository.addEvents(*events.toTypedArray())
    fakeRoomsRepository.addRooms(
      Room(1, "room1"),
      Room(2, "room2"),
    )
    fakeRepository.addTracks(
      Track("track1", "type1"),
      Track("track2", "type2"),
    )
  }

  internal class FakeRoomsRepository : RoomsRepository {
    val shouldCauseAnError: AtomicBoolean = AtomicBoolean(false)
    private val rooms = mutableListOf<Room>()

    override fun getRooms() = flow {
      if (shouldCauseAnError.get()) {
        throw RuntimeException("Error occurred while getting rooms")
      }
      emit(rooms.toList())
    }

    fun addRooms(vararg room: Room) {
      rooms.addAll(room)
    }

    fun rooms() = rooms.toList()

    fun clearRooms() {
      rooms.clear()
      shouldCauseAnError.set(false)
    }
  }
}
