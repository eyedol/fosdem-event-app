// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.common.SessionFilters
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.saturdayTab
import com.addhen.fosdem.ui.session.component.sundayTab
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.component.toFilterRoom
import com.addhen.fosdem.ui.session.component.toFilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchFilterUiState
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * This class encapsulates the setup of [SearchUiState] and its related
 * search or filter UI interactions.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseSearchSessionUiPresenter(
  eventsRepository: EventsRepository,
  roomsRepository: RoomsRepository,
) : Presenter<SessionSearchUiState> {

  private val filterTracks = eventsRepository.getTracks().map { results ->
    results.map { it.toFilterTrack() }
  }

  private val filterRooms = roomsRepository.getRooms().map { results ->
    results.map { it.toFilterRoom() }
  }

  private val events = combine(
    eventsRepository.getEvents(saturdayTab.date),
    eventsRepository.getEvents(sundayTab.date),
  ) { saturdayEvents, sundayEvents ->
    saturdayEvents + sundayEvents
  }

  private val searchFilters = MutableSharedFlow<SessionFilters>(
    replay = 1,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  val observeSearchFiltersAction: Flow<SearchUiState> = searchFilters
    .distinctUntilChanged()
    .flatMapLatest { obverseSearchUiState(dayTabs, it) }
    .distinctUntilChanged()

  protected fun onDaySelected(
    searchFilters: SessionFilters,
    dayTab: DayTab,
    isSelected: Boolean,
  ): SessionFilters {
    val selectedDays = searchFilters.days.toMutableList()
    return searchFilters.copy(
      days = selectedDays.apply {
        if (isSelected) add(dayTab) else remove(dayTab)
      }.toImmutableList(),
    )
  }

  protected fun onTrackSelected(
    searchFilters: SessionFilters,
    filterTrack: FilterTrack,
    isSelected: Boolean,
  ): SessionFilters {
    val selectedTracks = searchFilters.tracks.toMutableList()
    return searchFilters.copy(
      tracks = selectedTracks.apply {
        if (isSelected) add(filterTrack) else remove(filterTrack)
      }.toImmutableList(),
    )
  }

  protected fun onRoomSelected(
    searchFilters: SessionFilters,
    filterRoom: FilterRoom,
    isSelected: Boolean,
  ): SessionFilters {
    val selectedRooms = searchFilters.rooms.toMutableList()
    return searchFilters.copy(
      rooms = selectedRooms.apply {
        if (isSelected) add(filterRoom) else remove(filterRoom)
      }.toImmutableList(),
    )
  }

  protected fun onQueryChanged(searchFilters: SessionFilters, query: String): SessionFilters {
    return searchFilters.copy(searchQuery = query)
  }

  protected fun tryEmit(searchFilters: SessionFilters) = this.searchFilters.tryEmit(searchFilters)

  private fun obverseSearchUiState(
    days: PersistentList<DayTab>,
    searchFilters: SessionFilters,
  ): Flow<SearchUiState> = combine(
    filterTracks,
    filterRooms,
    events,
  ) { tracks, rooms, eventList ->
    val filters = searchFilters
    val filteredSessions = filterEvents(
      eventList,
      SessionFilters(
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
    filters: SessionFilters,
  ): PersistentMap<String, List<Event>> {
    var sessionItems = events.asSequence()
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
    return sessionItems.toList().sortAndGroupedEventsItems()
  }

  private fun searchUiStateListSearch(
    searchFilters: SessionFilters,
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
    searchFilters: SessionFilters,
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
}
