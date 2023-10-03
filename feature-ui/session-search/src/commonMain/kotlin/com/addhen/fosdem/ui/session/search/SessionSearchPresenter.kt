// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event
import com.addhen.fosdem.ui.session.search.component.SessionSearchSheetUiState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
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
        SessionSearchUiEvent.FilterAllBookmarks -> TODO()
        SessionSearchUiEvent.FilterFirstDayBookmarks -> TODO()
        SessionSearchUiEvent.FilterSecondDayBookmarks -> TODO()
        SessionSearchUiEvent.GoToPreviousScreen -> TODO()
        is SessionSearchUiEvent.ToggleSessionBookmark -> TODO()
      }
    }

    return SessionSearchUiState(
      content = sessionSheetPreview(),
      eventSink = ::eventSink,
    )
  }

  private fun sessionSheetPreview(): SessionSearchSheetUiState {
    return SessionSearchSheetUiState.ListSearch(
      sessionItemMap = sortAndGroupedEventsItems,
      isAllSelected = true,
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
  }

  val sortAndGroupedEventsItems = listOf(day1Event, day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()
}
