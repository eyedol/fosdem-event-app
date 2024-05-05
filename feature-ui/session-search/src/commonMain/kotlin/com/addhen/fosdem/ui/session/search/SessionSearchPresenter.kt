// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.onException
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
import kotlinx.coroutines.flow.catch
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
    var query by rememberSaveable { mutableStateOf("") }
    val uiMessageManager = remember { UiMessageManager() }
    val message by uiMessageManager.message.collectAsState(null)
    val appStrings = LocalStrings.current

    var selectedFilters by rememberSaveable(stateSaver = SessionFilters.Saver) {
      mutableStateOf(SessionFilters())
    }

    val searchUiState by observeSearchFiltersAction
      .catch {
        Logger.e(it) { "Error occurred" }
        uiMessageManager.emitMessage(UiMessage(it, actionLabel = appStrings.tryAgain))
      }.collectAsRetainedState(SearchUiState.Loading())

    LaunchedEffect(query) {
      selectedFilters = onQueryChanged(selectedFilters, query)
    }

    LaunchedEffect(selectedFilters) {
      emit(selectedFilters)
    }

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
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

        is SessionSearchUiEvent.ToggleSessionBookmark -> scope.launch {
          eventsRepository.value
            .toggleBookmark(event.eventId)
            .onException {
              Logger.e(it) { "Error occurred while toggling bookmark" }
              uiMessageManager.emitMessage(UiMessage(it))
            }
        }

        is SessionSearchUiEvent.QuerySearch -> query = event.query

        is SessionSearchUiEvent.ClearMessage -> scope.launch {
          uiMessageManager.clearMessage(event.messageId)
        }

        is SessionSearchUiEvent.TryAgain -> {
          // TODO: Implement a way to cause a retry functionality.
          // Attempted to do a `selectedFilters = SessionFilters()` but it doesn't emit
          // the filters to cause a reload because of how SessionSearchPresenter#searchFilters
          // MutableSharedFlow is configured.
        }
      }
    }

    return SessionSearchUiState(searchUiState, message, ::eventSink)
  }
}
