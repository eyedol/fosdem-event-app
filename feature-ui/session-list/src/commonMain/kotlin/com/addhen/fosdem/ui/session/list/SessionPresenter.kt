// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2
import com.addhen.fosdem.model.api.day2Event
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.dayTab1
import com.addhen.fosdem.ui.session.component.dayTab2
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionPresenter(
  @Assisted private val navigator: Navigator,
  private val repository: EventsRepository,
) : Presenter<SessionUiState> {
  @Composable
  override fun present(): SessionUiState {
    // val scope = rememberCoroutineScope()
    val days = listOf(dayTab1, dayTab2).toPersistentList()
    var isRefreshing by remember { mutableStateOf(false) }
    if (isRefreshing) {
      LaunchedEffect(Unit) {
        repository.refresh()
        isRefreshing = false
      }
    }

    val events by repository.getEvents().map { results ->
      when (results) {
        is AppResult.Error -> SessionSheetUiState.Error(days)
        is AppResult.Success -> SessionSheetUiState.Empty(days)
      }
    }.collectAsRetainedState(SessionSheetUiState.Empty(days))

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        is SessionUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        SessionUiEvent.SearchSession -> navigator.goTo(SessionSearchScreen)
        is SessionUiEvent.ToggleSessionBookmark -> TODO()
        SessionUiEvent.RefreshSession -> isRefreshing = true
      }
    }

    return SessionUiState(
      isRefreshing = isRefreshing,
      content = events,
      eventSink = ::eventSink,
    )
  }

  private fun sessionSheetPreview(): SessionSheetUiState {
    val sessionListUiState = SessionListUiState(
      sortAndGroupedEventsItems,
    )

    val sessionListUiState2 = SessionListUiState(
      sortAndGroupedEventsItems2,
    )

    val dayTab = day.toDayTab()
    val dayTab2 = day2.toDayTab()

    return SessionSheetUiState.ListSession(
      days = listOf(dayTab, dayTab2).toPersistentList(),
      sessionListUiStates = mapOf(
        dayTab to sessionListUiState,
        dayTab2 to sessionListUiState2,
      ),
    )
  }

  fun Day.toDayTab() = DayTab(
    id = id,
    date = date,
  )

  val sortAndGroupedEventsItems = listOf(day1Event, day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()

  val sortAndGroupedEventsItems2 = listOf(day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()
}
