// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.addhen.fosdem.ui.session.common.SessionFilters
import com.addhen.fosdem.ui.session.component.dayTab1
import com.addhen.fosdem.ui.session.component.dayTab2
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionBookmarkUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionBookmarkPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionBookmarkScreen -> {
        presenterFactory(navigator)
      }
      else -> null
    }
  }
}

@Inject
class SessionBookmarkPresenter(
  @Assisted private val navigator: Navigator,
  private val eventsRepository: EventsRepository,
) : BaseBookmarkSessionUiPresenter(eventsRepository) {
  @Composable
  override fun present(): SessionBookmarkUiState {
    val scope = rememberCoroutineScope()

    var selectedFilters by rememberSaveable(stateSaver = SessionFilters.Saver) {
      mutableStateOf(SessionFilters())
    }

    val bookmarkSheetUiState by observeSessionFiltersAction
      .collectAsRetainedState(SessionBookmarkSheetUiState.Loading())

    LaunchedEffect(selectedFilters) {
      tryEmit(selectedFilters)
    }

    fun eventSink(event: SessionBookmarkUiEvent) {
      when (event) {
        is SessionBookmarkUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }
        SessionBookmarkUiEvent.FilterAllBookmarks -> TODO()
        SessionBookmarkUiEvent.FilterFirstDayBookmarks -> {
          selectedFilters = onDaySelected(selectedFilters, dayTab1)
        }
        SessionBookmarkUiEvent.FilterSecondDayBookmarks -> {
          selectedFilters = onDaySelected(selectedFilters, dayTab2)
        }
        is SessionBookmarkUiEvent.ToggleSessionBookmark -> {
          scope.launch { eventsRepository.toggleBookmark(event.eventId) }
        }
      }
    }

    return SessionBookmarkUiState(
      content = bookmarkSheetUiState,
      eventSink = ::eventSink,
    )
  }
}
