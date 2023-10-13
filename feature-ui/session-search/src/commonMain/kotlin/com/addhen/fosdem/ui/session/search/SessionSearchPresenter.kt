// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.component.toFilterRoom
import com.addhen.fosdem.ui.session.component.toFilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchFilterUiState
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
    val scope = rememberCoroutineScope()

    val days by rememberRetained { mutableStateOf(dayTabs) }

    val filterTracks = eventsRepository.getTracks().map { results ->
      when (results) {
        is AppResult.Error -> emptyList()
        is AppResult.Success -> {
          results.data.map { it.toFilterTrack() }
        }
      }
    }

    val filterRooms = roomsRepository.getRooms().map { results ->
      when (results) {
        is AppResult.Error -> emptyList()
        is AppResult.Success -> {
          results.data.map { it.toFilterRoom() }
        }
      }
    }

    val events = eventsRepository.getEvents().map { results ->
      when (results) {
        is AppResult.Error -> emptyList()
        is AppResult.Success -> {
          // For testing purposes. Should be deleted before final release
          // val localResult = AppResult.Success(
          //  listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3),
          //)
          // val localResult = results
          results.data
        }
      }
    }

    val searchFilters: MutableStateFlow<SearchFilters> = MutableStateFlow(
      SearchFilters(),
    )

    val searchUiState by combine(
      filterTracks,
      filterRooms,
      searchFilters,
      events,
    ) { tracks, rooms, filters, eventList ->

      val filteredSearch = eventsFiltered(
        eventList,
        SearchFilters(
          days,
          filters.tracks,
          filters.rooms,
          searchQuery = filters.searchQuery,
        ),
      )

      if (filteredSearch.isEmpty()) {
        searchUiStateEmptySearch(
          filters,
          days,
          rooms.toImmutableList(),
          tracks.toImmutableList(),
        )
      } else {
        searchUiStateListSearch(
          filters,
          eventList.sortAndGroupedEventsItems().toPersistentMap(),
          days,
          rooms.toImmutableList(),
          tracks.toImmutableList(),
        )
      }
    }.collectAsState(SearchUiState.Loading())

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionSearchUiEvent.FilterDay -> {
          onDaySelected(searchFilters, event.dayTab, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionRoom -> {
          onRoomSelected(searchFilters, event.room, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionTrack -> {
          onTrackSelected(searchFilters, event.track, event.isSelected)
        }

        is SessionSearchUiEvent.ToggleSessionBookmark -> {
          scope.launch { eventsRepository.toggleBookmark(event.eventId) }
        }

        is SessionSearchUiEvent.QuerySearch -> {
          onQueryChanged(searchFilters, event.query)
        }

        SessionSearchUiEvent.GoToPreviousScreen -> navigator.pop()
      }
    }

    return SessionSearchUiState(
      searchUiState,
      ::eventSink,
    )
  }

  private fun eventsFiltered(
    events: List<Event>,
    filters: SearchFilters,
  ): PersistentMap<String, List<Event>> {
    var sessionItems = events
    if (filters.days.isNotEmpty()) {
      sessionItems = sessionItems.filter { timetableItem ->
        filters.days.contains(timetableItem.day.toDayTab())
      }
    }
    if (filters.tracks.isNotEmpty()) {
      sessionItems = sessionItems.filter { timetableItem ->
        filters.tracks.contains(timetableItem.track.toFilterTrack())
      }
    }
    if (filters.rooms.isNotEmpty()) {
      sessionItems = sessionItems.filter { timetableItem ->
        filters.rooms.contains(timetableItem.room.toFilterRoom())
      }
    }

    if (filters.searchQuery.isNotBlank()) {
      sessionItems = sessionItems.filter { timetableItem ->
        timetableItem.title.contains(
          filters.searchQuery,
          ignoreCase = true,
        ) ||
          timetableItem.abstractText.contains(
            filters.searchQuery,
            ignoreCase = true,
          ) ||
          timetableItem.description.contains(
            filters.searchQuery,
            ignoreCase = true,
          )
      }
    }
    return sessionItems.sortAndGroupedEventsItems().toPersistentMap()
  }

  private fun searchUiStateListSearch(
    searchFilters: SearchFilters,
    sessionItemMap: PersistentMap<String, List<Event>>,
    days: PersistentList<DayTab>,
    rooms: ImmutableList<FilterRoom>,
    tracks: ImmutableList<FilterTrack>,
  ): SearchUiState {
    return SearchUiState.ListSearch(
      sessionItemMap,
      query = SearchQuery(searchFilters.searchQuery),
      filterDayUiState = SearchFilterUiState(
        selectedItems = searchFilters.days.toImmutableList(),
        items = days.toImmutableList(),
      ),
      filterTrackUiState = SearchFilterUiState(
        selectedItems = searchFilters.tracks.toImmutableList(),
        items = tracks,
      ),
      filterRoomUiState = SearchFilterUiState(
        selectedItems = emptyList<FilterRoom>().toImmutableList(),
        items = rooms.toImmutableList(),
      ),
    )
  }

  private fun searchUiStateEmptySearch(
    searchFilters: SearchFilters,
    days: PersistentList<DayTab>,
    rooms: ImmutableList<FilterRoom>,
    tracks: ImmutableList<FilterTrack>,
  ): SearchUiState {
    return SearchUiState.Loading(
      query = SearchQuery(searchFilters.searchQuery),
      filterDayUiState = SearchFilterUiState(
        selectedItems = searchFilters.days.toImmutableList(),
        items = days.toImmutableList(),
      ),
      filterTrackUiState = SearchFilterUiState(
        selectedItems = searchFilters.tracks.toImmutableList(),
        items = tracks,
      ),
      filterRoomUiState = SearchFilterUiState(
        selectedItems = searchFilters.rooms.toImmutableList(),
        items = rooms,
      ),
    )
  }
}

private fun onDaySelected(
  searchFilters: MutableStateFlow<SearchFilters>,
  dayTab: DayTab,
  isSelected: Boolean,
) {
  val selectedDays = searchFilters.value.days.toMutableList()
  searchFilters.value = searchFilters.value.copy(
    days = selectedDays.apply {
      if (isSelected) add(dayTab) else remove(dayTab)
    },
  )
}

private fun onTrackSelected(
  searchFilters: MutableStateFlow<SearchFilters>,
  filterTrack: FilterTrack,
  isSelected: Boolean,
) {
  val selectedTracks = searchFilters.value.tracks.toMutableList()
  searchFilters.value = searchFilters.value.copy(
    tracks = selectedTracks.apply {
      if (isSelected) add(filterTrack) else remove(filterTrack)
    },
  )
}

private fun onRoomSelected(
  searchFilters: MutableStateFlow<SearchFilters>,
  filterRoom: FilterRoom,
  isSelected: Boolean,
) {
  val selectedRooms = searchFilters.value.rooms.toMutableList()
  searchFilters.value = searchFilters.value.copy(
    rooms = selectedRooms.apply {
      if (isSelected) add(filterRoom) else remove(filterRoom)
    },
  )
}

private fun onQueryChanged(
  searchFilters: MutableStateFlow<SearchFilters>,
  query: String,
) {
  searchFilters.value = searchFilters.value.copy(
    searchQuery = query,
  )
}
