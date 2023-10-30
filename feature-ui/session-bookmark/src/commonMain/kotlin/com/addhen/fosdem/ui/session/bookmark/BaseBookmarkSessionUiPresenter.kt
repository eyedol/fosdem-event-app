// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.addhen.fosdem.ui.session.common.SessionFilters
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.dayTab1
import com.addhen.fosdem.ui.session.component.dayTab2
import com.addhen.fosdem.ui.session.component.toDayTab
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * This class encapsulates the setup of [BookmarkUiState] and its related
 * filter UI interactions.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseBookmarkSessionUiPresenter(
  eventsRepository: EventsRepository,
) : Presenter<SessionBookmarkUiState> {

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

  private val sessionFilters = MutableSharedFlow<SessionFilters>(
    replay = 1,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  val observeSessionFiltersAction: Flow<SessionBookmarkSheetUiState> = sessionFilters
    .distinctUntilChanged()
    .flatMapLatest { obverseSearchUiState(it) }
    .distinctUntilChanged()

  protected fun onDaySelected(
    sessionFilters: SessionFilters,
    dayTab: DayTab,
  ): SessionFilters {
    val selectedDays = sessionFilters.days.toMutableList()
    return sessionFilters.copy(
      days = selectedDays.apply {
        if (contains(dayTab)) remove(dayTab) else add(dayTab)
      }.toImmutableList(),
    )
  }

  protected fun tryEmit(sessionFilters: SessionFilters) =
    this.sessionFilters.tryEmit(sessionFilters)

  private fun obverseSearchUiState(
    sessionFilters: SessionFilters,
  ): Flow<SessionBookmarkSheetUiState> = events.map { eventList ->
    val filters = sessionFilters
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
      sessionBookmarkSheetUiStateEmpty(filters)
    } else {
      sessionBookmarkSheetUiStateList(filters, filteredSessions)
    }
  }

  private fun filterEvents(
    events: List<Event>,
    filters: SessionFilters,
  ): PersistentMap<String, List<Event>> {
    var sessionItems = events
    if (filters.days.isNotEmpty()) {
      sessionItems = sessionItems.filter { sessionItem ->
        filters.days.contains(sessionItem.day.toDayTab())
      }
    }
    return sessionItems.sortAndGroupedEventsItems().toPersistentMap()
  }

  private fun sessionBookmarkSheetUiStateList(
    sessionFilters: SessionFilters,
    sessionItemMap: PersistentMap<String, List<Event>>,
  ): SessionBookmarkSheetUiState {
    return SessionBookmarkSheetUiState.ListBookmark(
      sessionItemMap,
      isDayFirstSelected = sessionFilters.days.contains(dayTab1),
      isDaySecondSelected = sessionFilters.days.contains(dayTab2),
      isAllSelected = false,
    )
  }

  private fun sessionBookmarkSheetUiStateEmpty(
    sessionFilters: SessionFilters,
  ): SessionBookmarkSheetUiState {
    return SessionBookmarkSheetUiState.Empty(
      isAllSelected = false,
      isDayFirstSelected = sessionFilters.days.contains(dayTab1),
      isDaySecondSelected = sessionFilters.days.contains(dayTab2),
    )
  }
}
