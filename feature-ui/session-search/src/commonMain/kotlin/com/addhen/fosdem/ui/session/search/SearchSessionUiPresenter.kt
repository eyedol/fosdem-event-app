// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import me.tatarka.inject.annotations.Inject

/**
 * This class encapsulates the setup of [SearchUiState] and its related
 * search or filter UI interactions.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Inject
class SearchSessionUiPresenter(
  eventsRepository: EventsRepository,
  roomsRepository: RoomsRepository,
) {

  private val filterTracks = eventsRepository.getTracks().map { results ->
    when (results) {
      is AppResult.Error -> emptyList()
      is AppResult.Success -> {
        results.data.map { it.toFilterTrack() }
      }
    }
  }

  private val filterRooms = roomsRepository.getRooms().map { results ->
    when (results) {
      is AppResult.Error -> emptyList()
      is AppResult.Success -> results.data.map { it.toFilterRoom() }
    }
  }

  private val events = eventsRepository.getEvents().map { results ->
    when (results) {
      is AppResult.Error -> emptyList()
      is AppResult.Success -> {
        // For testing purposes. Should be deleted before final release
        // val localResult = AppResult.Success(
        //  listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3),
        // )
        // val localResult = results
        listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3)
        // results.data
      }
    }
  }

  private var selectedSearchFilers = SearchFilters()
  private val searchFilters = MutableSharedFlow<SearchFilters>(
    replay = 1,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  val observeSearchFiltersAction: Flow<SearchUiState> = searchFilters
    .onStart { emit(selectedSearchFilers) }
    .distinctUntilChanged()
    .flatMapLatest { obverseSearchUiState(dayTabs, it) }
    .distinctUntilChanged()

  fun onDaySelected(
    dayTab: DayTab,
    isSelected: Boolean,
  ) {
    val selectedDays = selectedSearchFilers.days.toMutableList()
    tryEmit(
      selectedSearchFilers.copy(
        days = selectedDays.apply {
          if (isSelected) add(dayTab) else remove(dayTab)
        },
      ),
    )
  }

  fun onTrackSelected(
    filterTrack: FilterTrack,
    isSelected: Boolean,
  ) {
    val selectedTracks = selectedSearchFilers.tracks.toMutableList()
    tryEmit(
      selectedSearchFilers.copy(
        tracks = selectedTracks.apply {
          if (isSelected) add(filterTrack) else remove(filterTrack)
        },
      ),
    )
  }

  fun onRoomSelected(filterRoom: FilterRoom, isSelected: Boolean) {
    val selectedRooms = selectedSearchFilers.rooms.toMutableList()
    tryEmit(
      selectedSearchFilers.copy(
        rooms = selectedRooms.apply {
          if (isSelected) add(filterRoom) else remove(filterRoom)
        },
      ),
    )
  }

  fun onQueryChanged(query: String) = tryEmit(
    selectedSearchFilers.copy(searchQuery = query),
  )

  private fun obverseSearchUiState(
    days: PersistentList<DayTab>,
    searchFilters: SearchFilters,
  ): Flow<SearchUiState> = combine(
    filterTracks,
    filterRooms,
    events,
  ) { tracks, rooms, eventList ->
    selectedSearchFilers = searchFilters
    val filters = searchFilters
    val filteredSessions = filterEvents(
      eventList,
      SearchFilters(
        filters.days,
        filters.tracks,
        filters.rooms,
        searchQuery = filters.searchQuery,
      ),
    )

    if (filteredSessions.isEmpty()) {
      searchUiStateEmptySearch(
        filters,
        days,
        rooms.toImmutableList(),
        tracks.toImmutableList(),
      )
    } else {
      searchUiStateListSearch(
        filters,
        filteredSessions,
        days,
        rooms.toImmutableList(),
        tracks.toImmutableList(),
      )
    }
  }

  private fun filterEvents(
    events: List<Event>,
    filters: SearchFilters,
  ): PersistentMap<String, List<Event>> {
    var sessionItems = events
    if (filters.days.isNotEmpty()) {
      sessionItems = sessionItems.filter { sessionItem ->
        filters.days.contains(sessionItem.day.toDayTab())
      }
    }
    if (filters.tracks.isNotEmpty()) {
      sessionItems = sessionItems.filter { sessionItem ->
        filters.tracks.contains(sessionItem.track.toFilterTrack())
      }
    }
    if (filters.rooms.isNotEmpty()) {
      sessionItems = sessionItems.filter { sessionItem ->
        filters.rooms.contains(sessionItem.room.toFilterRoom())
      }
    }

    if (filters.searchQuery.isNotBlank()) {
      sessionItems = sessionItems.filter { sessionItem ->
        sessionItem.title.contains(
          filters.searchQuery,
          ignoreCase = true,
        ) ||
          sessionItem.abstractText.contains(
            filters.searchQuery,
            ignoreCase = true,
          ) ||
          sessionItem.description.contains(
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
        selectedValues = searchFilters.days.joinToString { it.title },
      ),
      filterTrackUiState = SearchFilterUiState(
        selectedItems = searchFilters.tracks.toImmutableList(),
        items = tracks,
        selectedValues = searchFilters.tracks.joinToString { it.name },
      ),
      filterRoomUiState = SearchFilterUiState(
        selectedItems = searchFilters.rooms.toImmutableList(),
        items = rooms.toImmutableList(),
        selectedValues = searchFilters.rooms.joinToString { it.name },
      ),
    )
  }

  private fun searchUiStateEmptySearch(
    searchFilters: SearchFilters,
    days: PersistentList<DayTab>,
    rooms: ImmutableList<FilterRoom>,
    tracks: ImmutableList<FilterTrack>,
  ): SearchUiState {
    return SearchUiState.Empty(
      query = SearchQuery(searchFilters.searchQuery),
      filterDayUiState = SearchFilterUiState(
        selectedItems = searchFilters.days.toImmutableList(),
        items = days.toImmutableList(),
        selectedValues = searchFilters.days.joinToString { it.title },
      ),
      filterTrackUiState = SearchFilterUiState(
        selectedItems = searchFilters.tracks.toImmutableList(),
        items = tracks,
        selectedValues = searchFilters.tracks.joinToString { it.name },
      ),
      filterRoomUiState = SearchFilterUiState(
        selectedItems = searchFilters.rooms.toImmutableList(),
        items = rooms,
        selectedValues = searchFilters.rooms.joinToString { it.name },
      ),
    )
  }

  private fun tryEmit(searchFilters: SearchFilters) = this.searchFilters.tryEmit(searchFilters)
}
