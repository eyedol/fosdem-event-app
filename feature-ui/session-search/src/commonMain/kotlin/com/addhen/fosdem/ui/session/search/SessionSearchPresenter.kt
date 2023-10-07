// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.room
import com.addhen.fosdem.model.api.room2
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.SearchQuery
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.component.toFilterRoom
import com.addhen.fosdem.ui.session.component.toFilterTrack
import com.addhen.fosdem.ui.session.search.component.SearchFilterUiState
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
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
) : Presenter<SessionSearchUiState> {
  @Composable
  override fun present(): SessionSearchUiState {
    // val scope = rememberCoroutineScope()

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionSearchUiEvent.FilterDay -> {
        }
        is SessionSearchUiEvent.FilterSessionRoom -> {}
        is SessionSearchUiEvent.FilterSessionTrack -> {}
        SessionSearchUiEvent.GoToPreviousScreen -> navigator.pop()
        is SessionSearchUiEvent.ToggleSessionBookmark -> {}
      }
    }

    return SessionSearchUiState(
      content = sessionSheetPreview(),
      eventSink = ::eventSink,
    )
  }

  private fun sessionSheetPreview(): SearchUiState {
    return SearchUiState.ListSearch(
      sortAndGroupedEventsItems,
      searchQuery = SearchQuery(""),
      searchFilterDayUiState = SearchFilterUiState(
        selectedItems = emptyList<DayTab>().toImmutableList(),
        items = listOf(day1Event.day.toDayTab(), day2Event1.day.toDayTab()).toImmutableList(),
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
        items = listOf(room.toFilterRoom(), room2.toFilterRoom()).toImmutableList(),
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
