// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.ui.session.common.SessionFilters
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
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
  roomsRepository: Lazy<RoomsRepository>,
  private val eventsRepository: Lazy<EventsRepository>,
) : BaseSearchSessionUiPresenter(eventsRepository, roomsRepository) {

  @Composable
  override fun present(): SessionSearchUiState {
    val scope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    var query by rememberSaveable { mutableStateOf("") }

    var selectedFilters by rememberSaveable(stateSaver = SessionFilters.Saver) {
      mutableStateOf(SessionFilters())
    }

    val searchUiState by observeSearchFiltersAction
      .collectAsRetainedState(SearchUiState.Loading())

    LaunchedEffect(query) {
      selectedFilters = onQueryChanged(selectedFilters, query)
    }

    LaunchedEffect(selectedFilters) {
      tryEmit(selectedFilters)
    }

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
          localFocusManager.clearFocus()
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionSearchUiEvent.FilterDay -> {
          selectedFilters = onDaySelected(selectedFilters, event.dayTab, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionRoom -> {
          selectedFilters = onRoomSelected(selectedFilters, event.room, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionTrack -> {
          selectedFilters = onTrackSelected(selectedFilters, event.track, event.isSelected)
        }

        is SessionSearchUiEvent.ToggleSessionBookmark -> {
          scope.launch { eventsRepository.value.toggleBookmark(event.eventId) }
        }

        is SessionSearchUiEvent.QuerySearch -> query = event.query
      }
    }

    return SessionSearchUiState(searchUiState, ::eventSink)
  }
}
