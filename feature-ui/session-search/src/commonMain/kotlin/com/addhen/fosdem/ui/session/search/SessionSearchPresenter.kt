// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.room
import com.addhen.fosdem.model.api.room2
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.toFilterRoom
import com.addhen.fosdem.ui.session.component.toFilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchFilterUiState
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionSearchUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionSearchPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionSearchScreen -> {
        presenterFactory(navigator)
      }

      else -> null
    }
  }
}

@Inject
class SessionSearchPresenter(
  @Assisted private val navigator: Navigator,
  private val eventsRepository: EventsRepository,
  private val roomsRepository: RoomsRepository,
) : Presenter<SessionSearchUiState> {
  @Composable
  override fun present(): SessionSearchUiState {
    // val scope = rememberCoroutineScope()

    val days by rememberRetained { mutableStateOf(dayTabs) }
    val filterRooms by roomsRepository.getRooms().map { results ->
      when (results) {
        is AppResult.Error -> emptyList()
        is AppResult.Success -> {
          results.data.map { it.toFilterRoom()}
        }
      }
    }.collectAsRetainedState(emptyList())

    val events by eventsRepository.getEvents().map { results ->
      when (results) {
        is AppResult.Error -> emptyList()
        is AppResult.Success -> {
          // For testing purposes. Should be deleted before final release
          val localResult = AppResult.Success(
            listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3),
          )
          // val localResult = results
          successSessionSheetUiSate(localResult, days)
        }
      }
    }.collectAsRetainedState(SessionSheetUiState.Loading(days))

    val filterStateFlow: MutableStateFlow<SearchFilters> = MutableStateFlow(
      SearchFilters(),
    )

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionSearchUiEvent.FilterDay -> {}
        is SessionSearchUiEvent.FilterSessionRoom -> {}
        is SessionSearchUiEvent.FilterSessionTrack -> {}
        SessionSearchUiEvent.GoToPreviousScreen -> navigator.pop()
        is SessionSearchUiEvent.ToggleSessionBookmark -> {}
      }
    }

    return SessionSearchUiState(
      content = sessionSheetPreview(days, filterRooms),
      eventSink = ::eventSink,
    )
  }

  private fun sessionSheetPreview(days: PersistentList<DayTab>, rooms: List<FilterRoom>): SearchUiState {
    return SearchUiState.ListSearch(
      sortAndGroupedEventsItems,
      searchQuery = SearchQuery(""),
      searchFilterDayUiState = SearchFilterUiState(
        selectedItems = emptyList<DayTab>().toImmutableList(),
        items = days.toImmutableList(),
      ),
      searchFilterSessionTrackUiState = SearchFilterUiState(
        selectedItems = emptyList<FilterTrack>().toImmutableList(),
        items = listOf(
          day1Event.track.toFilterTrack(),
          day2Event1.track.toFilterTrack(),
        ).toImmutableList(),
      ),
      searchFilterSessionRoomUiState = SearchFilterUiState(
        selectedItems = emptyList<FilterRoom>().toImmutableList(),
        items = rooms.toImmutableList(),
      ),
    )
  }

  val sortAndGroupedEventsItems = listOf(day1Event, day2Event1).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()
}
