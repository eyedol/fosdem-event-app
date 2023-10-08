// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.dayTab1
import com.addhen.fosdem.ui.session.component.dayTab2
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
    val scope = rememberCoroutineScope()
    val days = listOf(dayTab1, dayTab2).toPersistentList()
    var isRefreshing by rememberRetained { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
      if(isRefreshing) repository.refresh() else isRefreshing = false
    }

    val events by repository.getEvents().map { results ->
      when (results) {
        is AppResult.Error -> SessionSheetUiState.Error(days)
        is AppResult.Success -> {
          // For testing purposes. Should be deleted before final release
          val localResult = AppResult.Success(
            listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3),
          )
          //val localResult = results
          successSessionSheetUiSate(localResult, days)
        }
      }
    }.collectAsRetainedState(SessionSheetUiState.Empty(days))

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        is SessionUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionUiEvent.ToggleSessionBookmark -> {
          scope.launch { repository.toggleBookmark(event.eventId) }
        }

        SessionUiEvent.SearchSession -> navigator.goTo(SessionSearchScreen)
        SessionUiEvent.RefreshSession -> isRefreshing = true
      }
    }

    return SessionUiState(
      isRefreshing = isRefreshing,
      content = events,
      eventSink = ::eventSink,
    )
  }

  private fun successSessionSheetUiSate(
    results: AppResult.Success<List<Event>>,
    days: PersistentList<DayTab>,
  ): SessionSheetUiState {
    if (results.data.isEmpty()) return SessionSheetUiState.Empty(days)

    val sessionGroupedAndMapWithDays = groupAndMapEventsWithDays(results.data)
    return SessionSheetUiState.ListSession(
      days = days,
      sessionListUiStates = sessionGroupedAndMapWithDays,
    )
  }

  private fun groupAndMapEventsWithDays(
    events: List<Event>,
  ): PersistentMap<DayTab, SessionListUiState> {
    val groupEventsByDay = events.groupBy { it.day }
    val sessionsWithDays = mutableMapOf<DayTab, SessionListUiState>()
    groupEventsByDay.forEach { (key, events) ->
      val sortedAndGroupEvents = events.sortedBy { it.startTime }.groupBy {
        it.startTime.toString() + it.duration.toString()
      }.mapValues { entries ->
        entries.value.sortedWith(
          compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
        )
      }.toPersistentMap()
      sessionsWithDays[key.toDayTab()] = SessionListUiState(sortedAndGroupEvents)
    }
    return sessionsWithDays.toPersistentMap()
  }
}
